package com.naveen.eclipse.plugindev;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.naveen.eclipse.plugindev.constants.BundlePluginConstants;

/**
 * The activator class controls the plug-in life cycle
 * 
 * Singleton: plugin.xml - Activator.java only 1 per ECLIPSE BUNDLE !
 */
public class Activator extends AbstractUIPlugin { //implements DebugOptionsListener {

	// The plug-in ID
	public static final String PLUGIN_ID = BundlePluginConstants.PLUGIN_ID; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private IWindowListener focusListener;

	// EGIT.CORE: See also ActivatorPlus.java
	
	/**
	 * The constructor
	 */
	public Activator() {
		//		Activator.setActivator(this);	- EGIT.core.Activator
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	// See also: ActivatorPlus.java - based on EGIT.core
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
				// if (rcs.doReschedule)
				//	    rcs.schedule();
				//	refreshJob.triggerRefresh();
				//System.out.println("~~GVA: Activator.setupFocusHandling() focusListener - received 'windowActivated' event. DID NOTHING"); 
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
	public static Activator getDefault() {
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
// extends: public interface DebugOptionsListener extends EventListener
//			See also: ActivatorPlus.java	
	
	
}
