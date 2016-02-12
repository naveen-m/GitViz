package com.naveen.eclipse.gva.git;

import java.io.File;
import java.io.IOException;
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
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

import com.naveen.eclipse.gva.interfaces.component.IGitComponent;
import com.naveen.eclipse.gva.model.GitDataResponse;
import com.naveen.eclipse.gva.model.GitRepository;
import com.naveen.eclipse.gva.userViews.GitTestData;

/**
 * COMPONENT - JGIT access encapsulation
 * 
 * @author naveen naveen
 * @date 6/1/2015
 */
public class GitDataComponent implements IGitComponent {

	private boolean debug = true;
	
	/**
	 * Given a File
	 * 
	 * @param gitDir
	 * @return
	 * @throws IOException
	 */
	public synchronized GitDataResponse lookupRepository(final File gitDir)
			throws IOException {
		//Reference<Repository> r = null;
		//repositoryCache.get(gitDir);
		Repository repository = null;// = r != null ? r.get() : null;
		repository = FileRepositoryBuilder.create(gitDir);
		
		GitDataResponse retVal = new GitDataResponse();
			retVal.setGitRepository( new GitRepository(repository));
		
		return retVal;
	}	

    protected static void listRepositoryContents(Repository repository) throws IOException {
        ObjectId head = repository.resolve(Constants.HEAD);

        // a RevWalk allows walking over commits based on some filtering that is defined
        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head);
        RevTree tree = commit.getTree();
//if (debug) {   System.out.println("Having tree: " + tree);  }

        // now use a TreeWalk to iterate over all files in the Tree recursively
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        while (treeWalk.next()) {
            if (treeWalk.isSubtree()) {
                System.out.println("dir: " + treeWalk.getPathString());
                treeWalk.enterSubtree();
            } else {
                System.out.println("file: " + treeWalk.getPathString());
            }
        }
    }

	
	
	
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	protected static Git createGitComand(String pFilePath) {
		if (pFilePath != null) {
			try {
				return Git.open(new File( pFilePath ));
			} catch (IOException e) {
				System.out.println("ERROR ~ GitDataComponent ... IOException");
			}
		}

		return null;
	}

	/**
     * SALMA #1
	// IN:REPOSITORY.FILE		OUT:COMMIT HISTORY for FILE
     * @param pFileName 
     * @return
     * @throws Exception 
     */
	public static GitDataResponse requestHistoryCommitFromFile(Git pGitCommand, 
									final String pGitPath, 
									String pFileName) {
		
		System.out.println("~~GVA: GitDataComponent.requestHistoryCommitFromFile, pGitPath=" + pGitPath);
		System.out.println("~~GVA: GitDataComponent.requestHistoryCommitFromFile, pFileName=" + pFileName);
		
		// ensure non-null Git command
		if (pGitCommand == null) {
			pGitCommand = createGitComand(pGitPath);
		}

		// prepare to GIT a response
		GitDataResponse retVal = new GitDataResponse();
	   		retVal.setGitPath(pGitPath);
	   		retVal.setGitFileName(pFileName);
 
		// Get the Git Repository, associated with INPUT file name
        List<RevCommit> commitHistoryReturned = new ArrayList<RevCommit>();
		if (pGitCommand != null) {
	        Repository repo = pGitCommand.getRepository();
	        ObjectId head;

	        RevCommitList<RevCommit> commits = null;
			try {
				head = repo.resolve(Constants.HEAD);

				RevWalk revWalk = new RevWalk(repo);
		        RevCommit headRevCommit = revWalk.parseCommit(head);

		        // Given File, gather the Commit History
		        commits = getCommitHistory(pFileName, headRevCommit, pGitCommand);
			} catch (Exception e) {
				System.out.println("ERROR GitDataComponent JGIT FILE History" + e.getClass().getName() + " " + e.getMessage());
			}
			// IOException IncorrectObjectTypeException AmbiguousObjectException RevisionSyntaxException

			boolean debugMode = true;
	        int throttleLimit = 200;

	        for (RevCommit c : commits) {
	        	if (debugMode) { System.out.println(c);  }

	        	commitHistoryReturned.add(c);

	            throttleLimit--;
	            if (throttleLimit > 0) break;
	        }
		}
        
    	retVal.setFileHistory(commitHistoryReturned);
        	
        return retVal;
	}

    /**
     * SALMA #1
     * @param path source code file to retrieve complete history
     * @param start latest commit made for "path" to mark as start (tested with HEAD also)
     * @param pTempGit 
     * @return
     */
    protected static RevCommitList<RevCommit> getCommitHistory(String path, RevCommit start, Git pTempGit) throws Exception {

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

	
	

    /**
     * @date - 6/8 code snippit received via email from Jay @ 1:39am
     * Not sure where to add this, so I'm adding this snippit here .... It's kinda late to integrate
     */
	public String requestCloneForURI(String remoteURI) throws Exception {
	    
		String remote_URL = remoteURI + GitTestData.CLONE;

		String fileDirPath = GitTestData.GITDIR_LOCALUSER_JAY + 
							remoteURI.substring( remoteURI.lastIndexOf("/")+1)+"\\";

	    File temp = new File(fileDirPath);
	    Git git = Git.cloneRepository()
	    		.setURI(remote_URL)
	    		//.setCloneAllBranches(true)
	    		.setDirectory(temp)
	    		.call();		
	       
	    git.diff();  
	    Repository repo = git.getRepository();
	    //System.out.println("Path Cloned to: " + repo.getDirectory());
	    
	    return repo.getDirectory().toString();
	 }
	
	
	
}
