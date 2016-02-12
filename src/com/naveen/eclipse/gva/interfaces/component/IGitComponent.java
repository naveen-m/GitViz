package com.naveen.eclipse.gva.interfaces.component;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import com.naveen.eclipse.gva.model.GitDataResponse;

public interface IGitComponent {

	public GitDataResponse lookupRepository(final File gitDir)
			throws IOException;

	//public static GitDataResponse requestHistoryCommitFromFile(Git pGitCommand, 
	//		final String pGitPath, 
    //			String pFileName) {

}
