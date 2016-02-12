package com.naveen.eclipse.gva.dataviews;
//Salma: SUnday 6/7 @ 10pm .... REPOSITORY has_a FILES list

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import com.naveen.eclipse.gva.userViews.GitTestData;

/**
 * COMPONET = DATAVIEWs
 * sub data view: Repository Data from JGIT
 * 
 * @author naveen naveen
 * @date 6/5
 */
public class RepositoryDataView {

    static Git git;
private static boolean debug = true;

    public static void main(String[] args) {
    	
        String gitMetaDataPath = System.getProperty("user.home") +
        		GitTestData.BACKSLASH + GitTestData.GIT + 
        		GitTestData.BACKSLASH + GitTestData.REPO_JGITCOOK +
        		GitTestData.BACKSLASH + GitTestData.CLONE; 

        System.out.println( "Loading git location " + gitMetaDataPath + " ... ");
        try {
            git = Git.open(new File( gitMetaDataPath ));
            Repository repository = git.getRepository();
            listRepositoryContents(repository);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * It's "protected" from access outside of this DATAVIEW COMPONENT.
     * Since it's in the 'default' protected namespace of Java, no 
     * other components may access this method .... exception the 'DataViewComponent.java'
     */
    protected static void retrieveContentsForRespository() {
        String gitMetaDataPath = System.getProperty("user.home") +
        		GitTestData.BACKSLASH + GitTestData.GIT + 
        		GitTestData.BACKSLASH + GitTestData.REPO_JGITCOOK +
        		GitTestData.BACKSLASH + GitTestData.CLONE; 

        System.out.println( "Loading git location " + gitMetaDataPath + " ... ");
        try {
            git = Git.open(new File( gitMetaDataPath ));
            Repository repository = git.getRepository();
            listRepositoryContents(repository);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void listRepositoryContents(Repository repository) throws IOException {
        ObjectId head = repository.resolve(Constants.HEAD);

        // a RevWalk allows walking over commits based on some filtering that is defined
        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head);
        RevTree tree = commit.getTree();
        if (debug) {   System.out.println("Having tree: " + tree);  }

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
}