package rawcode;

// May 10 - Demo Day HTTP GitHub .... no JGIT. Nothing. Old. Delete

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder; 
//http://www.atetric.com/atetric/javadoc/org.eclipse.jgit/org.eclipse.jgit/3.4.0.201405281120-rc2/org/eclipse/jgit/storage/file/FileRepositoryBuilder.html
import org.eclipse.egit.*;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryUtil;

//import org.archstudio.myx.fw.AbstractMyxSimpleBrick;
//import org.archstudio.myx.fw.IMyxName;
//import org.archstudio.myx.java.demo.basic.App;
//import org.archstudio.myxgen.MyxGenBrick;

import com.naveen.eclipse.gva.interfaces.DataViewInterface;
import com.naveen.eclipse.gva.model.GVADoubleClickEventAction;

//TODO: Rename?  GitVizModelDAOService
public class NaveenUserProfileAuthentication2 implements DataViewInterface { //extends AbstractMyxSimpleBrick {
	
	private static String DEFAULT_GIT_REPOSITORY = "";
	private static String DEFAULT_GIT_FILEPATH = "";
	
 
	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void mainNO(String[] args) throws Throwable {
if(true) return;
		//MyxStubGenerationStatus foo = new MyxStubGenerationStatus("fooA", null, "fooB", null);
//		App demoApp = new App();
		//MyxGenBrick myXBrick = new MyxGenBrick(null);
//		demoApp.doApp1();
		
		boolean debug = false;
		if (debug) {
			naveenDemoDay();
			return;
		}
		
		// EGIT v3.4 Eclipse .....
		//https://github.com/eclipse/egit

		try {
			//DESPARATION #1
			String thatRepository = "https://github.com/sampsyo/beets"; // "https://github.com/eclipse/egit"
					//			    https://github.com/sampsyo/beets/blob/master/beet
			File gitDirArgument = new File(thatRepository);
			Repository dRepository001 = new FileRepositoryBuilder() //
				.setGitDir(gitDirArgument) // --git-dir if supplied, no-op if null
				//.readEnviroment() // scan environment GIT_* variables
				.findGitDir() // scan up the file system tree
				.build();
dRepository001.getDirectory();
dRepository001.getWorkTree();

				//UNIT Test #1
			File gitDir = new File("myFirstSundayGit.txt");
			Repository tmpRepository = FileRepositoryBuilder.create(gitDir);
			tmpRepository.create();  //NPE
			tmpRepository.close();
			// use repository instance from RepositoryCache!
			Repository repository = Activator.getDefault().getRepositoryCache().lookupRepository(gitDir);
			//Repository repository = new Repository(gitDir);
			//repository = Repository.getRepository();
			
			RepositoryUtil util = Activator.getDefault().getRepositoryUtil();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void naveenDemoDay() throws MalformedURLException,
			IOException {
		String link = "https://raw.githubusercontent.com/sampsyo/beets/master/beets/ui/commands.py"; // You can change the raw url to whichever Github source code .
		boolean debug = true;
		if (debug) {
			//link = "https://github.com/UWBArchitectureSpr15/visualGit15/blob/master/testMayFile.txt";
			link = "https://raw.githubusercontent.com/UWBArchitectureSpr15/visualGit15/master/testMayFile.txt";
		}
		
		URL rawUrl = new URL(link);
		HttpURLConnection rawHttp = (HttpURLConnection) rawUrl.openConnection();
		Map<String, List<String>> rawHeader = rawHttp.getHeaderFields();
 
		// If URL is getting 301 and 302 redirection HTTP code then get new URL link.
		// This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
		for (String header : rawHeader.get(null)) {
			if (header.contains(" 302 ") || header.contains(" 301 ")) {
				link = rawHeader.get("Location").get(0);
				rawUrl = new URL(link);
				rawHttp = (HttpURLConnection) rawUrl.openConnection();
				rawHeader = rawHttp.getHeaderFields();
			}
		}
		InputStream gitStream = rawHttp.getInputStream();
		String rawResponse = rawGetStringFromStream(gitStream);
		System.out.println(rawResponse);
		/*
		*/
	}
 
        // ConvertStreamToString() Utility - we name it as rawGetStringFromStream()
	private static String rawGetStringFromStream(InputStream gitStream) throws IOException {
		if (gitStream != null) {
			Writer gitWriter = new StringWriter();
 
			char[] gitBuffer = new char[2048];
			try {
				Reader gitReader = new BufferedReader(new InputStreamReader(gitStream, "UTF-8"));
				int counter;
				while ((counter = gitReader.read(gitBuffer)) != -1) {
					gitWriter.write(gitBuffer, 0, counter);
				}
			} finally {
				gitStream.close();
			}
			return gitWriter.toString();
		} else {
			return "No Contents";
		}
	}

	
	
	
	//@Override - AbstractMyxSimpleBrick
//	public Object getServiceObject(IMyxName arg0) {
		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public GVADoubleClickEventAction getGVADatAction() {
		// TODO Auto-generated method stub
		return null;
	}
}