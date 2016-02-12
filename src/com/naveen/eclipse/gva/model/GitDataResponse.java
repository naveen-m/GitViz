package com.naveen.eclipse.gva.model;

import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

public class GitDataResponse {

	private boolean gitFileIsLocal = false;	//default: remote GitHub.com
	private GitRepository mGitRepository;
	
	private List<RevCommit> mListHistoryFileCommit;


	
	
	
	
	public GitRepository getGitRepository() {
		return mGitRepository;
	}
	public void setGitRepository(GitRepository pGitRepository) {
		this.mGitRepository = pGitRepository;
	}
	public void setFileHistory(List<RevCommit> commitHistoryReturned) {
		mListHistoryFileCommit = commitHistoryReturned;
	}
	public List<RevCommit> getFileHistory() {
		return mListHistoryFileCommit;
	}
	public void setGitFileName(String pFileName) {
		// TODO Auto-generated method stub
		
	}
	public void setGitPath(String pGitPath) {
		// TODO Auto-generated method stub
		
	}
	
	// number of commits history, for a given Git file, if applicable
	public int getCountFileCommitHistory() {
		if (mListHistoryFileCommit!=null) {
			return mListHistoryFileCommit.size();
		}
		return 0;
	}
}
