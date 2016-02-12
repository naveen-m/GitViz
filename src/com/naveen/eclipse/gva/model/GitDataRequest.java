package com.naveen.eclipse.gva.model;

/**
 * Convenience Request object.
 * 
 * Facilitates "decouple" of the Component interfaces, and MODULARIZATION of codebase:\
 * 
 * 1.) USERVIEWCOMPONENT - are for GUI user input/output, not JGIT access
 * 2.) DATAVIEWCOMPONENT - are for Business Rules, not JGIT access
 * 3.) GITCOMPONENT - is for using the actual JGIT API 
 * 
 * @author naveen naveen
 * @date 6/5/2015
 */
public class GitDataRequest {

	public static final String RESPOSITORY_FILES = "RespositoryFiles";
	public static final String FILEHISTORY       = "FileHistory";
	public static final String FILETAGLIST       = "FileTagList";
	public static final String ISSUELABEL        = "IssueLabel";

	private String mRequestAction;
	private boolean gitFileIsLocal = false;	//default: remote GitHub.com
	private String remoteGitURL;
	private GitRepository mGitRepository;

	public void setAction(final String pRequestType) {
		mRequestAction = pRequestType;
	}
	public String getAction() {
		return mRequestAction;
	}
	
	public void setGitRepositoryName(final GitRepository pGitRepository) {
		mGitRepository = pGitRepository;
	}
	public String getGitRepositoryName() {
		if (mGitRepository!=null) {
			return mGitRepository.getGitRepositoryName();
		}
		return null;
	}

	protected boolean isGitFileIsLocal() {
		return gitFileIsLocal;
	}

	protected void setGitFileIsLocal(boolean gitFileIsLocal) {
		this.gitFileIsLocal = gitFileIsLocal;
	}
	public void setDirLocal(String tLocalGitHomePath) {
		mGitRepository = new GitRepository("", true);
		gitFileIsLocal = true;
	}
	public void setRemoteDirURI(String pRemoteURI) {
		mGitRepository = new GitRepository("", false);
		remoteGitURL = pRemoteURI;
		gitFileIsLocal = false;
	}

}
