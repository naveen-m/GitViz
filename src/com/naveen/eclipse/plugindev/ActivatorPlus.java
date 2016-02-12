package com.naveen.eclipse.plugindev;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Dictionary;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.egit.core.GitProvider;
import org.eclipse.egit.core.JobFamilies;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCache;
import org.eclipse.egit.core.internal.job.JobUtil;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.core.op.IgnoreOperation;
import org.eclipse.egit.core.project.GitProjectData;
import org.eclipse.egit.core.project.RepositoryFinder;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.core.securestorage.EGitSecureStore;
import org.eclipse.egit.ui.internal.ConfigurationChecker;
import org.eclipse.egit.ui.internal.credentials.EGitCredentialsProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.util.FS;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugOptionsListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.naveen.eclipse.plugindev.constants.BundlePluginConstants;

/**
 * The activator class controls the plug-in life cycle
 * 
 * Singleton: plugin.xml - Activator.java only 1 per ECLIPSE BUNDLE !
 */
public class ActivatorPlus extends AbstractUIPlugin implements DebugOptionsListener {

	// The plug-in ID
	public static final String PLUGIN_ID = BundlePluginConstants.PLUGIN_ID2; //$NON-NLS-1$

	// The shared instance
	private static ActivatorPlus plugin;
	private IWindowListener focusListener;

	// EGIT.CORE:
	private static String pluginId;
	private RepositoryCache repositoryCache;
	private IndexDiffCache indexDiffCache;
	private RepositoryUtil repositoryUtil;
	private EGitSecureStore secureStore;
	//private AutoShareProjects shareGitProjectsJob;
	private IResourceChangeListener preDeleteProjectListener;
	private IgnoreDerivedResources ignoreDerivedResourcesListener;
	
	// EGIT.UI:
	//private RepositoryChangeScanner rcs;
	//private ResourceRefreshJob refreshJob;
	private ListenerHandle refreshHandle;
	private DebugOptions debugOptions;

	
	/**
	 * The constructor
	 */
	public ActivatorPlus() {
		ActivatorPlus.setActivator(this);	//- EGIT.core.Activator
	}

	/**
	 * @return the name of this plugin
	 */
	public static String getPluginId() {
		return pluginId;
	}

	/**
	 * Utility to create an error status for this plug-in.
	 *
	 * @param message User comprehensible message
	 * @param thr cause
	 * @return an initialized error status
	 */
	public static IStatus error(final String message, final Throwable thr) {
		return new Status(IStatus.ERROR, getPluginId(), 0,	message, thr);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		boolean isEGITCore = false;
		boolean isEGITUI = false;
		if (isEGITCore) {
			//super.start(context);
			pluginId = context.getBundle().getSymbolicName();

			// we want to be notified about debug options changes
			Dictionary<String, String> props = new Hashtable<String, String>(4);
			props.put(DebugOptions.LISTENER_SYMBOLICNAME, pluginId);
			context.registerService(DebugOptionsListener.class.getName(), this,
					props);

			//repositoryCache = new RepositoryCache();
			indexDiffCache = new IndexDiffCache();
			try {
				GitProjectData.reconfigureWindowCache();
			} catch (RuntimeException e) {
//				logError(CoreText.Activator_ReconfigureWindowCacheError, e);
			}
			GitProjectData.attachToWorkspace(true);

	//		IEclipsePreferences node = InstanceScope.INSTANCE.getNode(ActivatorPlus.getPluginId());
	//		String gitPrefix = node.get(GitCorePreferences.core_gitPrefix, null);
	//		if (gitPrefix != null)
	//			FS.DETECTED.setGitPrefix(new File(gitPrefix));

	//		repositoryUtil = new RepositoryUtil();

	//		secureStore = new EGitSecureStore(SecurePreferencesFactory.getDefault());

			registerAutoShareProjects();
			registerAutoIgnoreDerivedResources();
			registerPreDeleteResourceChangeListener();
		}
		
		if (isEGITUI) {
			// we want to be notified about debug options changes
			Dictionary<String, String> props = new Hashtable<String, String>(4);
			props.put(DebugOptions.LISTENER_SYMBOLICNAME, context.getBundle()
					.getSymbolicName());
			context.registerService(DebugOptionsListener.class.getName(), this,
					props);

			setupSSH(context);
			setupProxy(context);
			setupRepoChangeScanner();
			setupRepoIndexRefresh();
			setupFocusHandling();
			setupCredentialsProvider();
			ConfigurationChecker.checkConfiguration();			
		}
		
		
		plugin = this;
	}

	private void setupFocusHandling() {
		focusListener = new IWindowListener() {

			public void windowOpened(IWorkbenchWindow window) {
				// nothing
			}

			public void windowDeactivated(IWorkbenchWindow window) {
				// nothing
			}

			public void windowClosed(IWorkbenchWindow window) {
				// nothing
			}

			public void windowActivated(IWorkbenchWindow window) {
//				if (rcs.doReschedule)
//					rcs.schedule();
//				refreshJob.triggerRefresh();
				System.out.println("~~GVA: Activator.setupFocusHandling() focusListener - received 'windowActivated' event. DID NOTHING"); 
			}
		};
		PlatformUI.getWorkbench().addWindowListener(focusListener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ActivatorPlus getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
// ~~~~~~ EGIT.core Activator Extensions
	private static void setActivator(ActivatorPlus a) {
		plugin = a;
	}

// EGIT.CORE extends:
//public interface DebugOptionsListener extends EventListener
	@Override
	public void optionsChanged(DebugOptions options) {
		// TODO Auto-generated method stub
		
	}

	private void registerPreDeleteResourceChangeListener() {
		if (preDeleteProjectListener == null) {
			preDeleteProjectListener = new IResourceChangeListener() {

				public void resourceChanged(IResourceChangeEvent event) {
					IResource resource = event.getResource();
					if (resource instanceof IProject) {
						IProject project = (IProject) resource;
						if (project.isAccessible()) {
				//			if (RepositoryProvider.getProvider(project) instanceof GitProvider) {
				//				IResource dotGit = project
				//						.findMember(Constants.DOT_GIT);
				//				if (dotGit != null
				//						&& dotGit.getType() == IResource.FOLDER)
				//					GitProjectData.reconfigureWindowCache();
				//			}
						} else {
							// bug 419706: project is closed - use java.io API
							IPath locationPath = project.getLocation();
							if (locationPath != null) {
				//				File locationDir = locationPath.toFile();
				//				File dotGit = new File(locationDir,
				//						Constants.DOT_GIT);
				//				if (dotGit.exists() && dotGit.isDirectory()) {
				//					GitProjectData.reconfigureWindowCache();
				//				}
							}
						}
					}
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(preDeleteProjectListener, IResourceChangeEvent.PRE_DELETE);
		}
	}

	
	private void registerAutoShareProjects() {
		//shareGitProjectsJob = new AutoShareProjects();
		//ResourcesPlugin.getWorkspace().addResourceChangeListener(
		//		shareGitProjectsJob, IResourceChangeEvent.POST_CHANGE);
	}

	private static class AutoShareProjects implements
			IResourceChangeListener {

		private static int INTERESTING_CHANGES = 0; //IResourceDelta.ADDED | IResourceDelta.OPEN;

		public AutoShareProjects() {
			// empty
		}

		private boolean doAutoShare() {
			IEclipsePreferences d = DefaultScope.INSTANCE.getNode(ActivatorPlus.getPluginId());
			IEclipsePreferences p = InstanceScope.INSTANCE.getNode(ActivatorPlus.getPluginId());
			return p.getBoolean(GitCorePreferences.core_autoShareProjects, d
					.getBoolean(GitCorePreferences.core_autoShareProjects,
							true));
		}

		public void resourceChanged(IResourceChangeEvent event) {
			try {

				final Map<IProject, File> projects = new HashMap<IProject, File>();

				event.getDelta().accept(new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta delta)
							throws CoreException {
						return visitConnect(delta, projects);
					}
				});

				if (projects.size() > 0							||									false ) {  // BUG is here, ClassCastExec
					ConnectProviderOperation op = null; //new ConnectProviderOperation(projects);
					JobUtil.scheduleUserJob(op,
							CoreText.Activator_AutoShareJobName,
							JobFamilies.AUTO_SHARE);
				}

			} catch (CoreException e) {
				ActivatorPlus.logError(e.getMessage(), e);
				return;
			}
		}

		private boolean visitConnect(IResourceDelta delta,
				final Map<IProject, File> projects) throws CoreException {
			if (!doAutoShare())
				return false;
			if (delta.getKind() == IResourceDelta.CHANGED
					&& (delta.getFlags() & INTERESTING_CHANGES) == 0)
				return true;
			final IResource resource = delta.getResource();
			if (!resource.exists() || !resource.isAccessible() ||
					resource.isLinked(IResource.CHECK_ANCESTORS))
				return false;
			if (resource.getType() != IResource.PROJECT)
				return true;
			if (RepositoryMapping.getMapping(resource) != null)
				return false;
			final IProject project = (IProject) resource;
	//		RepositoryProvider provider = RepositoryProvider.getProvider(project);
			// respect if project is already shared with another
			// team provider
	//		if (provider != null)
	//			return false;															//	SERIOUS RUNTIME BUG POTENTIAL
			RepositoryFinder f = new RepositoryFinder(project);
			f.setFindInChildren(false);
			Collection<RepositoryMapping> mappings = f.find(new NullProgressMonitor());
			if (mappings.size() != 1)
				return false;

			RepositoryMapping m = mappings.iterator().next();
			IPath gitDirPath = m.getGitDirAbsolutePath();
			if (gitDirPath.segmentCount() == 0)
				return false;

			IPath workingDir = gitDirPath.removeLastSegments(1);
			// Don't connect "/" or "C:\"
			if (workingDir.isRoot())
				return false;

	//		File userHome = FS.DETECTED.userHome();
	//		if (userHome != null) {
	//			Path userHomePath = new Path(userHome.getAbsolutePath());
				// Don't connect "/home" or "/home/username"
	//			if (workingDir.isPrefixOf(userHomePath))
	//				return false;
	//		}

			// connect
	//		final File repositoryDir = gitDirPath.toFile();
	//		projects.put(project, repositoryDir);

			try {
	//			Activator.getDefault().getRepositoryUtil()
	//					.addConfiguredRepository(repositoryDir);
			} catch (IllegalArgumentException e) {
				logError(CoreText.Activator_AutoSharingFailed, e);
			}
			return false;
		}
	}

	private void registerAutoIgnoreDerivedResources() {
		ignoreDerivedResourcesListener = new IgnoreDerivedResources();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				ignoreDerivedResourcesListener,
				IResourceChangeEvent.POST_CHANGE);
	}

	private static class IgnoreDerivedResources implements
			IResourceChangeListener {

		protected boolean autoIgnoreDerived() {
			IEclipsePreferences d = DefaultScope.INSTANCE.getNode(ActivatorPlus.getPluginId());
			IEclipsePreferences p = InstanceScope.INSTANCE.getNode(ActivatorPlus.getPluginId());
			return p.getBoolean(
					GitCorePreferences.core_autoIgnoreDerivedResources,
					d.getBoolean(
							GitCorePreferences.core_autoIgnoreDerivedResources,
							true));
		}

		public void resourceChanged(IResourceChangeEvent event) {
			try {
				IResourceDelta d = event.getDelta();
				if (d == null || !autoIgnoreDerived())
					return;

				final Set<IPath> toBeIgnored = new LinkedHashSet<IPath>();

				d.accept(new IResourceDeltaVisitor() {

					public boolean visit(IResourceDelta delta)
							throws CoreException {
						if ((delta.getKind() & (IResourceDelta.ADDED | IResourceDelta.CHANGED)) == 0)
							return false;
						int flags = delta.getFlags();
						if ((flags != 0)
								&& ((flags & IResourceDelta.DERIVED_CHANGED) == 0))
							return false;

						final IResource r = delta.getResource();
						// don't consider resources contained in a project not
						// shared with Git team provider
						if ((r.getProject() != null)
								&& (RepositoryMapping.getMapping(r) == null))
							return false;
						if (r.isTeamPrivateMember())
							return false;

						if (r.isDerived()) {
							try {
								if (!RepositoryUtil.isIgnored(r.getLocation()))
									toBeIgnored.add(r.getLocation());
							} catch (IOException e) {
						//		logError(MessageFormat.format(
						//						CoreText.Activator_ignoreResourceFailed,
						//						r.getFullPath()), e);
							}
							return false;
						}
						return true;
					}
				});
				if (toBeIgnored.size() > 0)
					JobUtil.scheduleUserJob(new IgnoreOperation(toBeIgnored),
							CoreText.Activator_autoIgnoreDerivedResources,
							JobFamilies.AUTO_IGNORE);
			} catch (CoreException e) {
				ActivatorPlus.logError(e.getMessage(), e);
				return;
			}
		}
	}
	
	public static void logError(final String message, final Throwable thr) {
		getDefault().getLog().log(
				new Status(IStatus.ERROR, getPluginId(), 0, message, thr));
	}

// ~~~~~~~~~ EGIT.UI Activator
	private void setupSSH(final BundleContext context) {
		final ServiceReference ssh;

	//	ssh = context.getServiceReference(IJSchService.class.getName());
	//	if (ssh != null) {
	//		SshSessionFactory.setInstance(new EclipseSshSessionFactory(
	//				(IJSchService) context.getService(ssh)));
	//	}
	}

	private void setupProxy(final BundleContext context) {
		final ServiceReference proxy;

	//	proxy = context.getServiceReference(IProxyService.class.getName());
	//	if (proxy != null) {
	//		ProxySelector.setDefault(new EclipseProxySelector(
	//				(IProxyService) context.getService(proxy)));
	//		Authenticator.setDefault(new EclipseAuthenticator(
	//				(IProxyService) context.getService(proxy)));
	//	}
	}	
	
	private void setupRepoChangeScanner() {
	//	rcs = new RepositoryChangeScanner();
	//	rcs.setSystem(true);
	//	rcs.schedule(RepositoryChangeScanner.REPO_SCAN_INTERVAL);
	}

	private void setupRepoIndexRefresh() {
	//	refreshJob = new ResourceRefreshJob();
		refreshHandle = Repository.getGlobalListenerList()
				.addIndexChangedListener(			null				);		// WIP refreshJob);
	}
	
	private void setupCredentialsProvider() {
		CredentialsProvider.setDefault(new EGitCredentialsProvider());
	}

}
