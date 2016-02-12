package com.naveen.eclipse.gva.model;

import org.eclipse.jgit.lib.Repository;

public class GitRepository {

	public static String REPOSITORY_NAME_KEY = "gitRepositoryName";
	public static String GIT_HOME_LOCAL_PATH_KEY = "userLocalGitHomePath";
	
	private String mGitRepositoryName;
	private boolean gitFileIsLocal = false;	//default: remote GitHub.com

	// ---------
	public GitRepository(final String tGitRepositoryName) {
		mGitRepositoryName = tGitRepositoryName;
	}
	public GitRepository(final String tGitRepositoryName, boolean pLocal) {
		mGitRepositoryName = tGitRepositoryName;
		gitFileIsLocal = pLocal;
	}

	public GitRepository(Repository repository) {
		if (repository != null) {
			//mGitRepositoryName = repository.get
		}
	}
	protected String getGitRepositoryName() {
		return mGitRepositoryName;
	}
	protected void setGitRepositoryName(String mGitRepositoryName) {
		this.mGitRepositoryName = mGitRepositoryName;
	}


}
