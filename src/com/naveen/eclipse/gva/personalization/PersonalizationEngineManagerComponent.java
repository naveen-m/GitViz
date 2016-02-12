package com.naveen.eclipse.gva.personalization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Event;

import com.naveen.eclipse.gva.dataviews.DataViewComponent;
import com.naveen.eclipse.gva.interfaces.component.IPersonalizationTopicEngine;

/**
 * COMPONENT - PERSONALIZATION TOPIC MODELING
 * v1.0: Data Capture
 * v2.0: Basic Topic Modeling algorithms, processing semantic relevance (future)
 * v3.0: Machine Learning Algorithms to process data captured (future)
 * 
 * @author naveen naveen
 * @date 5/29/2015
 */
public class PersonalizationEngineManagerComponent implements IPersonalizationTopicEngine {

	/**
	 * The is the primary "communication in" means by which DataViews (or any other allowable 
	 * component communicates with the Topic Data storage. The 'Personalization' comes in a 
	 * future release of GitViz when we can derive relevance from the user/system events
	 * and make observations and/or recommendations that are useful to the end-user (Software
	 * Developer accessing GitHub)
	 * 
	 * @param pEvent
	 * @return
	 */
	public static boolean notfiyTopicProcessEvent(PersonalizedTopicEvent pEvent) {
		
		if ( pEvent != null ) {
			System.out.println( "~~~GVA: PersonalizationEngineComponent Time Topic: "
					+ new Date() + " "
					+ pEvent.toString() );
			
		}
	
		return true;
	}
//	public boolean notfiyTopicProcessEvent(PersonalizedTopicEvent pEvent) {
	//	notfiyTopicProcessEvent(pEvent);
//	}

	/**
	 * Instantiate a new
	 * Not quite a pseudo-factory, but does help control the Domain of object creation for the
	 * richness of the 'PersonalizationTopicEvent'
	 * 
	 * @param pGitUserName
	 * @param pViewSelectedElement
	 * @param pTokenMap
	 * @return PersonalizedTopicEvent
	 */
	public static PersonalizedTopicEvent requestCreateTopicEvent(
			final String pGitUserName, 
			final String pViewSelectedElement, 
			Map<String, Object> pTokenMap) {
		
		Map<String,Object> updatedTokens = new HashMap<String,Object>();
		if (!pTokenMap.isEmpty()) {
			updatedTokens.putAll(pTokenMap);
			// Always a new Map, but add-to the existing TokenMap keys if any pre-existed
		}
		
		//TODO: add more variables for the future
		return new PersonalizedTopicEvent(
				null, 
				new Date(), 	// the timestamp is a particularly useful/helpful dimension of information
				null,
				updatedTokens);
	}
	
	/**
	 * 
	 * 
	 * @param v
	 * @param pUserName
	 * @return PersonalizedStatistics
	 */
	public PersonalizedStatistics personalizeRecommendation(
			DataViewComponent v, 
			final String pUserName) {

		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation

		return new PersonalizedStatistics();
	}	
	
	
	public PersonalizedStatistics getUsagePatterns(final String pUserID, 
								final String pTopicCategory, 
								int frequency) 	{

		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation
		// NOTE: for future GitViz 2.0 implementation

		return new PersonalizedStatistics();
	}


	private List<Object> listenersForPersonalizedTopicNotifications;

	public boolean registerListener() {
		return true;
	}

}


