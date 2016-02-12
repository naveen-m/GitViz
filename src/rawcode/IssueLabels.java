package rawcode;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.eclipse.egit.github.core.SearchRepository;
//import org.eclipse.egit.github.core.client.GitHubClient;
//import org.eclipse.egit.github.core.service.RepositoryService;
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


//	SALMA - Sunday 10pm

public class IssueLabels 
{
	private static Map<String, String> searchQuery = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        
    	/*GitHubClient client = new GitHubClient();
        //client.setCredentials("jsnvyas", "password");
        RepositoryService service = new RepositoryService(client);
        //service.
        searchQuery.put("keyword","java"); 
        //searchQuery.put("size","304");
        List<SearchRepository> searchRes = service.searchRepositories(searchQuery);
        System.out.println("Search result "+searchRes.toString());*/
      

	File gitWorkDir = new File("C:/temp/gittest/");
    Git git = Git.open(gitWorkDir);
    Repository repo = git.getRepository();
    
    ObjectId lastCommitId = repo.resolve(Constants.HEAD);
 
    RevWalk revWalk = new RevWalk(repo);
    RevCommit commit = revWalk.parseCommit(lastCommitId);
 
    RevTree tree = commit.getTree();
 
    TreeWalk treeWalk = new TreeWalk(repo);
    treeWalk.addTree(tree);
    treeWalk.setRecursive(true);
    treeWalk.setFilter(PathFilter.create("file1.txt"));
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
    }
}
