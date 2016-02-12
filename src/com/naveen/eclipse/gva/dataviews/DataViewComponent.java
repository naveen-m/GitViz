package com.naveen.eclipse.gva.dataviews;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import com.naveen.eclipse.gva.dataviews.files.FileHistoryDataView;
import com.naveen.eclipse.gva.dataviews.taglist.TagList;
import com.naveen.eclipse.gva.dataviews.taglist.UIFrame;
import com.naveen.eclipse.gva.git.GitDataComponent;
import com.naveen.eclipse.gva.interfaces.component.IDataViewComponent;
import com.naveen.eclipse.gva.model.GitDataRequest;
import com.naveen.eclipse.gva.model.GitDataResponse;
import com.naveen.eclipse.gva.personalization.PersonalizationEngineManagerComponent;
import com.naveen.eclipse.gva.personalization.PersonalizedTopicEvent;
import com.naveen.eclipse.gva.userViews.GitTestData;
import com.naveen.eclipse.plugindev.constants.UITextContants;
import com.naveen.eclipse.plugindev.views.IssueLabels;

/**
 * COMPONENT = DATAVIEWS
 * 
 * This is a Component design for the GitViz Architecture.
 * The key feature is the limited number of public Interface access points 'IN'. The 
 * purposeful design promotes the Modularity and Extensibility decoupling method
 * signatures and forcing clients to call defined Interface.
 * 
 * TODO: implement 'cache' in Future v2.0 (EGIT fork?)
 * 
 * @author naveen naveen
 * @date 6/1/2015
 */
public class DataViewComponent implements IDataViewComponent {
	
	private GitDataComponent gitDataComponent = new GitDataComponent();
	
	/**
	 * Public Component Input Request
	 * 
	 * @param gdr - GitDataRequest
	 * @param pte - PersonalizedTopicEvent
	 */
	public GitDataResponse requestGitData(GitDataRequest gdr, PersonalizedTopicEvent pte) {
		if (gdr==null) return null;

		GitDataResponse returnResponse = null;

		// Step 1. Process incoming DataView request, by RequestAction type
		if (GitDataRequest.RESPOSITORY_FILES.equalsIgnoreCase(gdr.getAction())) {

			RepositoryDataView.retrieveContentsForRespository();

		} else if (GitDataRequest.FILEHISTORY.equalsIgnoreCase(gdr.getAction())) {

			getFileHistoryDataView(gdr, pte);

		} else if (GitDataRequest.FILETAGLIST.equalsIgnoreCase(gdr.getAction())) {
			//TODO: Jay, Naveen Integration with "DATAVIEW Framework" pending
		}

		// Step 2. Notify the Personalization Topic Modeler Engine 
		//		Strategy: post-successful access to GIT HUB, so that request was vali. Pre-GIT is the other strategy.
		PersonalizationEngineManagerComponent.notfiyTopicProcessEvent(pte);
		
		return returnResponse;
	}

    /**
     * ARCHITECTURE NOTE:
     * Extensible, internal interface.
     * 1.) access is restricted to these internal objects within the DATAVIEW
     * 2.) accessors are 'protected' to enable 'extension-point' (Extensibility, Scalability) of system
     * 
     * @param gdr
     * @param pte
     * @return GitDataResponse 
     */
    protected GitDataResponse getFileHistoryDataView(
    		final GitDataRequest gdr, 
    		PersonalizedTopicEvent pte) { 
    	
    	GitDataResponse returnResponse = GitDataComponent.requestHistoryCommitFromFile(
    			null,
				GitTestData.GITDIR_LOCALUSER + 
				  GitTestData.SLASH + GitTestData.REPO_JGITCOOK, 			
				GitTestData.FILENAME_JGITCOOK);
		//TODO: cleanup GitVizUserView - since data hardcoded here
    	
    	return returnResponse;
	}


	// INTEGRATION: WIP Monday 6/8 1:00pm ..... very very late from Naveen
	public static void requestRunNaveensTagListCode(String pRemoteURI) {
		
		try {
			String  tags = null;

			// JGIT library access
			if (pRemoteURI != null) {
				tags = new TagList().gettagList(pRemoteURI);
			}
		
			// post: display to user GUI
			UIFrame win = new UIFrame(UITextContants.ALERT_USER_TAG_LIST);
			win.uiWindow.setVisible(true);
			if (tags != null) {
				win.setText(tags);
			}
		} catch (InvalidRemoteException e) {
			System.out.println("~~GVA Exeception TagList InvalidRemoteException");
			//e.printStackTrace();
		} catch (TransportException e) {
			System.out.println("~~GVA Exeception TagList TransportException");
			//e.printStackTrace();
		} catch (GitAPIException e) {
			System.out.println("~~GVA Exeception TagList GitAPIException");
			//e.printStackTrace();
		}
	}
	
	// INTEGRATION: WIP Monday 6/8 6:30am ..... very very late from Jay
	public static void requestRunJaysCode(String pRemoteURI) {

		try {
			IssueLabels clonerep = new IssueLabels();
			String  clonedPath=null;
			//remoteURI = JOptionPane.showInputDialog(UITextContants.PROMPT_REMOTE_GIT_URL);

			// JGIT library access
			if (pRemoteURI != null) {
				clonedPath = clonerep.getClone(pRemoteURI);
			}
			
			JOptionPane.showMessageDialog(null, "Cloned to FilePAth: " + clonedPath);
		} catch (HeadlessException e) {
			System.out.println("~~GVA Exeception clonerep.getClone HeadlessException");
			//e.printStackTrace();
		} catch (Exception e) {
			System.out.println("~~GVA Exeception clonerep.getClone HeadlessException");
			//e.printStackTrace();
		}
	}

	
}
