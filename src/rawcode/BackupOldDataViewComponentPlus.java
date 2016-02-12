package rawcode;

import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;

import com.naveen.eclipse.gva.dataviews.files.FileHistoryDataView;
import com.naveen.eclipse.gva.dataviews.taglist.TagList;
import com.naveen.eclipse.gva.dataviews.taglist.UIFrame;
import com.naveen.eclipse.gva.interfaces.component.IDataViewComponent;
import com.naveen.eclipse.gva.model.GitDataRequest;
import com.naveen.eclipse.gva.model.GitDataResponse;
import com.naveen.eclipse.gva.personalization.PersonalizedTopicEvent;
import com.naveen.eclipse.plugindev.views.IssueLabels;

/**
 * @author naveen naveen
 */
public class BackupOldDataViewComponentPlus implements IDataViewComponent { // extends AbstractMyxSimpleBrick {

	//private NaveensIssueLabelsView view1 = new NaveensIssueLabelsView();
	//		new PersonalizationEngineManagerComponent(), new SalmaFileHistoryDataView());

	
// ~~~~~~~~~~~~~~~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public List<RevCommit> requestFileHistory(final String pFilePath, final PersonalizedTopicEvent pPte) {
		//List<RevCommit> retVal = data.mFileHistoryDataView.notifyReturnFileHistory(pFilePath);
		//data.peManager.notfiyTopicProcessEvent(pPte);
		return null; //retVal;
    }
	
// ~~~~~~~~~~~~~~~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
	
	
	//declare interfaces
//we	public static final IMyxName IDATAVIEW_COMPONENT_OUT = MyxUtils.createName("out_interface");
//tried	public static final IMyxName IDATAVIEW_COMPONENT_IN  = MyxUtils.createName("in_interface");
			
	//private IView viewer; // Lecture Slide#27 doesn't work .... ArchStudio5.
	
	//@Override - AbstractMyxSimpleBrick.class .............. STUPID ARCHSTUDIO FRAMEWORK
//we tried	public Object getServiceObject(IMyxName pMyxName) {
//		return null;	// default=null, if no interfaces are going in
//	}

	public static void requestRunnaveensCode() {	// TODO Refactor method name, remove 'naveen' ... extend 'IMyxName'	
		// TODO 
	}
	//public static void requestRunSalmasCode() {
		//SalmaFileHistoryDataView.main(null);
		//SalmaFileHistoryDataView.getFileHistory();
	//}

	public static void requestRunNaveensCode() {

		try {
			IssueLabels clonerep = new IssueLabels();
			String  clonedPath, remoteURI;
			remoteURI = JOptionPane.showInputDialog("Enter github resource URI :");
					
			clonedPath = clonerep.getClone(remoteURI);
			
			JOptionPane.showMessageDialog(null, "Cloned to FilePAth: " + clonedPath);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void requestRunJaysRespositoryHeadTagsCode() {
		
		try {
			String  tags, remoteURI;
			remoteURI = JOptionPane.showInputDialog("Enter github resource URI :");
				
			tags = new TagList().gettagList(remoteURI);
		
			UIFrame win = new UIFrame("Tag List for the selected repository");
			
			win.uiWindow.setVisible(true);
			win.setText(tags);
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// ARCHITECTURE NOTE: there is no access to internal DATAVIEW from the outside
	// ARCHITECTURE NOTE: there is no access to internal DATAVIEW from the outside
	// ARCHITECTURE NOTE: there is no access to internal DATAVIEW from the outside
	// ARCHITECTURE NOTE: there is no access to internal DATAVIEW from the outside
	// ARCHITECTURE NOTE: there is no access to internal DATAVIEW from the outside
    protected FileHistoryDataView getFileHistoryDataView() {
		return null;//data.mFileHistoryDataView;
	}


	protected void setFileHistoryDataView(
			FileHistoryDataView fileHistoryDataView) {
		//data.mFileHistoryDataView = fileHistoryDataView;
	}

	@Override
	public GitDataResponse requestGitData(GitDataRequest gdr,
			PersonalizedTopicEvent pte) {
		// TODO Auto-generated method stub
		return null;
	}



	
}
