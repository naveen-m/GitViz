// NOTE: Jay's SUNDAY 11pm FileSet - 1 of many .....
package com.naveen.eclipse.plugindev.views;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import org.eclipse.jgit.lib.Ref;


import java.io.ByteArrayOutputStream;

 
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;




public class IssueLabels 
{
	
/* Salma's Code
	
	public static void main(String[] args) throws Exception
	{
	
	 File gitWorkDir = new File("C:/Users/Jay/Eclipse Plugins/SoftwareArchitectureWorkspace/com.naveen.eclipse.pluginDev/bin/Sample/.git");
	    
	 Git git = Git.open(gitWorkDir);
	    Repository repo = git.getRepository();
	 
	    ObjectId lastCommitId = repo.resolve(Constants.HEAD);
	 
	    RevWalk revWalk = new RevWalk(repo);
	    RevCommit commit = revWalk.parseCommit(lastCommitId);
	 
	    RevTree tree = commit.getTree();
	 
	    TreeWalk treeWalk = new TreeWalk(repo);
	    treeWalk.addTree(tree);
	    treeWalk.setRecursive(true);
	    treeWalk.setFilter(PathFilter.create("file1"));
	    if (!treeWalk.next()) 
	    {
	      System.out.println("Nothing found!");
	      return;
	    }
	 
	    ObjectId objectId = treeWalk.getObjectId(0);
	    ObjectLoader loader = repo.open(objectId);
	 
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    loader.copyTo(out);
	    System.out.println("file1.txt:\n" + out.toString());
	}*/
	
	
	public String getClone(String remoteURI) throws Exception
	 {
	    
		String REMOTE_URL = remoteURI +".git";
	    File temp = new File("C:\\Users\\Jay\\Eclipse Plugins\\SoftwareArchitectureWorkspace\\com.naveen.eclipse.pluginDev\\bin\\"
	    						+remoteURI.substring(remoteURI.lastIndexOf("/")+1)+"\\");
	    Git git = Git.cloneRepository()
	    		.setURI(REMOTE_URL)
	    		//.setCloneAllBranches(true)
	    		.setDirectory(temp)
	    		.call();		
	       
	    git.diff();  
	    Repository repo = git.getRepository();
	    System.out.println("Path Cloned to: " + repo.getDirectory());
	    
	    return repo.getDirectory().toString();
	    
	 }
	
	


	/*
	private static final String REMOTE_URL = "https://github.com/pengwynn/flint.git";

    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call();

        try {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        System.out.println("Having repository: " + result.getRepository().getDirectory());
        } finally {
        	result.close();
        }
    }*/
}
