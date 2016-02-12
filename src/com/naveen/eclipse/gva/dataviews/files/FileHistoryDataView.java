package com.naveen.eclipse.gva.dataviews.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevWalk;

import com.naveen.eclipse.gva.git.GitDataComponent;
import com.naveen.eclipse.gva.interfaces.DataViewInterface;
import com.naveen.eclipse.gva.model.GVADoubleClickEventAction;
import com.naveen.eclipse.gva.model.GitDataResponse;
import com.naveen.eclipse.gva.userViews.GitTestData;

/**
 * COMPONET = DATAVIEWs
 * sub data view: File History Data from JGIT
 * 
 * @author naveen naveen
 * @date 6/1/2015
 */
public class FileHistoryDataView implements DataViewInterface {
    
    private static boolean debugMode = true;
    
    public static void main( String[] args ) {
    	if (debugMode) {
            System.out.println( "Hello World!" );
            System.out.println( System.getProperty("user.home") );
    	}
        retrieveHistoryFromGitFile(GitTestData.GITDIR_LOCALUSER + GitTestData.REPO_JGITCOOK, 
        		"README.md");			// defaults - main static run
    }

	/**
	 * API: Word Document - Version Sat 6/7 9:00pm
	 * @return List<RevCommit>
	 */
    //public List<RevCommit> getFileHistory(File pFile) {
	//public List<ChangeSet> getFileHistory(File pFile) {
    public static GitDataResponse notifyReturnFileHistory(final String pFilePath, final String pFileName) {  // FILEname="README.md"
	//public static List<RevCommit> notifyReturnFileHistory(final String pFilePath, final String pFileName) {  // FILEname="README.md"
	    	//List<RevCommit> salmaReturnType = retrieveHistoryFromGitFile(pFilePath, pFileName);	//SALMA's old
	        //return salmaReturnType;

	    	return GitDataComponent.requestHistoryCommitFromFile(null, pFilePath, pFileName);	//naveen's new
    }

	@Override
	public GVADoubleClickEventAction getGVADatAction() {
		// TODO Step#1: Salma to run her code ... look at Console
		// TODO Step#2: Salma to "integrate" her code into Eclipse 
		//				architectural plugin/model
		return null;
	}

	private static List<RevCommit> retrieveHistoryFromGitFile(final String pGitFilePath, final String pFileName) {

		List<RevCommit> retVal = new ArrayList<RevCommit>();
        Git tempGit = null;			int throttleLimit = 200;

        try {
        	
        	if (pGitFilePath != null) {
        		tempGit = Git.open(new File( pGitFilePath ));
        	} else {
        		return null;
        	}
            Repository repo = tempGit.getRepository();
            ObjectId head = repo.resolve(Constants.HEAD);
            RevWalk revWalk = new RevWalk(repo);
            RevCommit headRevCommit = revWalk.parseCommit(head);
            RevCommitList<RevCommit> commits = getCommitHistory(pFileName, headRevCommit, tempGit);
            
            for (RevCommit c : commits) {
            	if (debugMode) {
            		System.out.println(c); 
            	}
                retVal.add(c);
            }
        } catch (Exception e) {
        	System.out.println("Error Exception occurred: SalmaFileHistoryDataView.java");
        	if (debugMode) {
                e.printStackTrace();
        	}
        }
		
		return retVal;
	}


    /**
     * 
     * @param path source code file to retrieve complete history
     * @param start latest commit made for "path" to mark as start (tested with HEAD also)
     * @param pTempGit 
     * @return
     */
    static RevCommitList<RevCommit> getCommitHistory(String path, RevCommit start, Git pTempGit) throws Exception {

            Config config = new Config(pTempGit.getRepository().getConfig());
            	config.setString("diff", null, "renames", "copies");
            	config.setInt("diff", null, "renameLimit", Integer.MAX_VALUE);
            DiffConfig diffConfig = config.get(DiffConfig.KEY);

            final RevWalk revWalk = new RevWalk(pTempGit.getRepository());
            revWalk.setTreeFilter(FollowFilter.create(path, diffConfig));
            revWalk.markStart(revWalk.parseCommit(start));

            final RevCommitList<RevCommit> list = new RevCommitList<RevCommit>();
            list.source(revWalk);
            list.fillTo(Integer.MAX_VALUE);

            return list;
    }
	
	
}
