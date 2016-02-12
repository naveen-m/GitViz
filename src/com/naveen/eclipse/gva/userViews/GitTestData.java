package com.naveen.eclipse.gva.userViews;

/**
 * Stub Test Data - Constants and "workarounds" for the minimal/missing GUI UI.
 * Our group did not work on the Visualization presentation, only the Data Model
 * 
 */
public class GitTestData {
	public static final String GIT = "git";

	public static final String GITDIR_LOCALUSER = "/Users/dd3152/" + GIT;
	// For example: /Users/dd3152/git/jgit-cookbook/.git
	//              ^^^^^^^^^^^^^^^^
	// Use this canonical File System form for Linux, also if you are on Windows!!
	
	public static final String SLASH = "/";			// canonical file systems
	public static final String BACKSLASH = "\\";	// Windows file systems
	public static final String CLONE = ".git";

	// Pre-GUI integration, must code your Windows user path (local user).
	//			Jay, that path from today 6/8 ... I have no idea how to use that??
	public static final String GITDIR_LOCALUSER_JAY = "C:\\Users\\Jay\\Eclipse Plugins\\SoftwareArchitectureWorkspace\\com.naveen.eclipse.pluginDev\\bin\\";

	// Test Git Repositories
	public static final String REPO_JGITCOOK = "jgit-cookbook";
		public static final String FILENAME_JGITCOOK = "README.md";	// History File, from that repository
	
//Notes:		
//static String workingPath = "/Users/salma_000/git/jgit-cookbook";	// defaults - main static run
//	    static String workingPath = "/Users/dd3152/git/jgit-cookbook";	// defaults - main static run
//	    static String gitMetaDataPath = workingPath + "/.git";				// defaults - main static run
//"C:/temp/gittest/";	// Windows

		// Salma "/git/jgit-cookbook/.git"


}
