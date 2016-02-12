package com.naveen.eclipse.gva.dataviews.taglist;
/*
   Copyright 2013, 2014 Dominik Stadler
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.util.Collection;

//import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;


/**
 * COMPONET = DATAVIEWs
 * sub data view: File History Data from JGIT
 * 
 * Simple snippet which shows how to show diffs between branches
 * 
 * @author Naveen Mohan
 * @date 6/8/2015
 */
public class TagList {

	//private static final String REMOTE_URL = "https://github.com/pengwynn/flint.git";
	public String tagList;
	
	public String gettagList(String remoteURL) throws InvalidRemoteException, TransportException, GitAPIException {
        // then clone
        //System.out.println("Listing remote repository " + REMOTE_URL);
		
		remoteURL += ".git";
		
		System.out.println("~~GVA: TagList.java Ready to call JGIT then Internet URL: " + remoteURL);
		
		String eachtag;
        Collection<Ref> refs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(remoteURL)
                .call();
        // http://www.kernel.org/pub/software/scm/git/docs/git-ls-remote.html
        //			Head/Tag=true - constrain to only Heads/Tags
        
        for (Ref ref : refs) {
            eachtag = ref.toString();
            eachtag = eachtag.substring(eachtag.lastIndexOf("/")+1, eachtag.length()-1);
            
            tagList += eachtag +"\n";
        }
        
        return tagList;
    }
}