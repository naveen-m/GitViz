package com.naveen.eclipse.gva.interfaces.component;

import com.naveen.eclipse.gva.model.GitDataRequest;
import com.naveen.eclipse.gva.model.GitDataResponse;
import com.naveen.eclipse.gva.personalization.PersonalizedTopicEvent;

/**
 * COMPONENT = DATAVIEWS
 * 
 * This is a Component design for the GitViz Architecture.
 * 
 * @author naveen naveen
 * @date 6/1/2015
 */
public interface IDataViewComponent {

	/**
	 * The usage of this Interface is 'static' for efficiency programmatically.
	 * However, because Java Interfaces cannot support the keyword 'static' this is a 'Marker' style
	 * interface.
	 * 
	 * @param gdr
	 * @param pte
	 * @return GitDataResponse
	 */
	public GitDataResponse requestGitData(GitDataRequest gdr, PersonalizedTopicEvent pte);

}
