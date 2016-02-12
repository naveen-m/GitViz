package com.naveen.eclipse.gva.cloud;

import com.naveen.eclipse.gva.model.GitRepository;
import com.naveen.eclipse.gva.model.GitUser;

/**
 * Future Implementation - Envisioned Design of GitViz 3.0
 * Network Cloud Persistence data storage, for collaboration of local machine's 
 * Personalized Topic Model data.
 * 
 * @author naveen naveen
 */
public class GitVizPersonalizedCloud {

//	Provided Interface:
//		GitVizUsageData retrieveUsagePatternsForUser(GitUser pUserID, GitHubRepository pRepository, String pCategory, ) – Exposed API for use by the “Personalization Engine” Component
//		Required Interface:
//		boolean storageUsagePatternsForUser(GitUser pUserID, GitHubRepository pRepository, String pCategory, ) – Exposed API for use by the “Personalization Engine” Component. 

	public GitVizUsageData retrieveUsagePatternsForUser(
			GitUser pGitUserID, 
			GitRepository pRepository, 
			String[] pTopicCategory) {
		
		// Design Placeholder - for future GitViz 3.0
		// Design Placeholder - for future GitViz 3.0
		// Design Placeholder - for future GitViz 3.0
		
		return new GitVizUsageData();
	}

			
}
