package com.naveen.eclipse.gva2.userViews;


import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import rawcode.BackupOldDataViewComponentPlus;

import com.naveen.eclipse.gva.dataviews.DataViewComponent;
import com.naveen.eclipse.gva.model.GitDataRequest;
import com.naveen.eclipse.gva.model.GitDataResponse;
import com.naveen.eclipse.gva.model.GitRepository;
import com.naveen.eclipse.gva.personalization.PersonalizationEngineManagerComponent;
import com.naveen.eclipse.gva.personalization.PersonalizedTopicEvent;
import com.naveen.eclipse.gva.userViews.GitTestData;
import com.naveen.eclipse.plugindev.constants.BundlePluginConstants;
import com.naveen.eclipse.plugindev.constants.UITextContants;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

/**
 * @author naveen naveen
 * @date 05/16/2015 (v0.5) 05/23 (v0.6) 05/30 (v0.7) 06/07 (v0.8)
 * @date 06/08/2015 (v0.9) - REFACTOR WIP SUnday 6/7 @ 7:45pm .....
 * 
 * 		NOTE: this is the 'GitViz View' (from Eclipse Menu > View) ..... it is not a simple 'View' panel from . 
 * 			That is a completely different kind of 'View'.
 */
public class GitVizPlusView extends ViewPart {
	
	// ~~~~~~~~~~~~~~~~~ THE SO-CALLED "PREMIUM UI" VIEW ~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = BundlePluginConstants.VIEW_FREE_ID;//"com.naveen.eclipse.gva.userViews.GitVizUserView";
	
	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	//protected Action doubleClickAction; //private Action doubleClickAction;
	protected BackupOldDataViewComponentPlus mDataViewComponent = new BackupOldDataViewComponentPlus();

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			// COMPONENT: UserView (v1.0) Minimal - displays SWT/JFACE icons to click on		
			return new String[] { UITextContants.TASK_1_REPOSITORYFILES, 
									UITextContants.TASK_2_FILEHISTORY, 
									UITextContants.TASK_3_ISSUELABEL_JAY, 
									UITextContants.TASK_4_GUI_TAGLIST,
									UITextContants.TASK_5_VISUALIZE_STREAM};
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor. - Simple "FREE" View #1
	 */
	public GitVizPlusView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		//TODO: my guess ... is that we need to add more here for the "Big Impressive Visual Bang Stuff" we want ???
		//TODO: my guess ... is that we need to add more here for the "Big Impressive Visual Bang Stuff" we want ???
		//TODO: my guess ... is that we need to add more here for the "Big Impressive Visual Bang Stuff" we want ???
		//TODO: my guess ... is that we need to add more here for the "Big Impressive Visual Bang Stuff" we want ???
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), BundlePluginConstants.VIEWER_ID_SPACE);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				GitVizPlusView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	protected void makeActions() {   //	private void makeActions() {
		
		action01Defined();
		
		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		actionDoubleClickDefined(); 
	}


	/**
	 * GitViz - Double-Click Event received in UI
	 * USER VIEW COMPONENT ... calls
	 * DATA VIEW COMPONENT
	 */
	private void actionDoubleClickDefined() {
		
		doubleClickAction = new Action() {
			
			// CONNECTOR Eclipse Event User Buffer (Display.class) - minimizes user latency, by running as a Thread
			public void run() {
				ISelection selectionEvent = viewer.getSelection();		// Compare: IF "naveens Repository"
				Object publishedSelectionElement = ((IStructuredSelection)selectionEvent).getFirstElement(); 
										// showMessage("Double-click detected on "+obj.toString());

				String tLocalGitHomePath  = GitTestData.GITDIR_LOCALUSER;			// "/Users/dd3152/git/";
				String tGitRepositoryName = GitTestData.REPO_JGITCOOK;
										//WIP	userInputGitRepositoryName = JOptionPane.showInputDialog(this,"Enter Repository name:");

				
				// Topic Modeling - Personalization Event record stamp
				Map<String, Object> tokenMap = new HashMap<String, Object>();
					tokenMap.put(GitRepository.REPOSITORY_NAME_KEY, tGitRepositoryName);
					tokenMap.put(GitRepository.GIT_HOME_LOCAL_PATH_KEY, tLocalGitHomePath);
				
				PersonalizedTopicEvent pte = PersonalizationEngineManagerComponent.requestCreateTopicEvent(
						null, 
						publishedSelectionElement.toString(),
						tokenMap);				

				// Component REQUEST to DataViewComponent
				GitDataRequest gdr = new GitDataRequest();
					gdr.setDirLocal(tLocalGitHomePath);
				
				// TASK #1: Given Respository, get the Files ....
				if (UITextContants.TASK_1_REPOSITORYFILES.equalsIgnoreCase(publishedSelectionElement.toString())) {
					gdr.setAction(GitDataRequest.RESPOSITORY_FILES);

					processGUIResponse( mDataViewComponent.requestGitData(gdr, pte), 
											GitDataRequest.RESPOSITORY_FILES); //requestRunnaveensCode();

				// TASK #2: Given a File, get the CommitHistory (Revisions list)	
				} else if (UITextContants.TASK_2_FILEHISTORY.equalsIgnoreCase(publishedSelectionElement.toString())) {
					gdr.setGitRepositoryName( new GitRepository(tGitRepositoryName));
					gdr.setAction(GitDataRequest.FILEHISTORY);

					processGUIResponse( mDataViewComponent.requestGitData(gdr, pte), 
											GitDataRequest.FILEHISTORY);

					// TASK #2: Given a File, get the associated Issue Labels 	
				} else if (UITextContants.TASK_3_ISSUELABEL_JAY.equalsIgnoreCase(publishedSelectionElement.toString())) {
					//DataViewComponentOldVersion1.requestRunJaysRespositoryHeadTagsCode();
					String remoteURI = JOptionPane.showInputDialog(UITextContants.PROMPT_REMOTE_GIT_URL);

					DataViewComponent.requestRunJaysCode(remoteURI);

					//gdr.setRemoteDirURI(remoteURI);
					//gdr.setAction(GitDataRequest.ISSUELABEL);
					//mDataViewComponent.requestGitData(gdr, pte);
					
				} else if (UITextContants.TASK_4_GUI_TAGLIST.equalsIgnoreCase(publishedSelectionElement.toString())) {
					String remoteURI = JOptionPane.showInputDialog(UITextContants.PROMPT_REMOTE_GIT_URL);
					
					DataViewComponent.requestRunNaveensTagListCode(remoteURI);
					
					//gdr.setRemoteDirURI(remoteURI);
					//gdr.setAction(GitDataRequest.FILETAGLIST);
					//mDataViewComponent.requestGitData(gdr, pte);

				} else if (UITextContants.TASK_5_VISUALIZE_STREAM.equalsIgnoreCase(publishedSelectionElement.toString())) {
					showMessage(UITextContants.ALERT_USER_NO_ACTION);
				}
			}

			private void processGUIResponse(
					final GitDataResponse requestGitDataResponse,
					final String pUserActionRequest) {
				//NOTE: GUI Visualization may occur here. Use the 'GitDataResponse' object's data, plus the UserAction requested
				//NOTE: GUI Visualization may occur here. Use the 'GitDataResponse' object's data, plus the UserAction requested
				//NOTE: GUI Visualization may occur here. Use the 'GitDataResponse' object's data, plus the UserAction requested
					// Team #9b is working on Visualization of the data 
			}
			
		};//end definition of "new Action" for Double Click (Start-up time)
	}// end Event Callback (runtime Double-click in GUI)


	private void action01Defined() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed - OnLoad() this View=" + getClass().getName());
			}
		};
		action1.setText("Action 1 - Loaded View:" + getClass().getName());
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			UITextContants.DIALOG_MSG_001, //"GitViz Sat May16 - Test View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}