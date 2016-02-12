package com.naveen.eclipse.gva.interfaces.component;

import com.naveen.eclipse.gva.dataviews.DataViewComponent;
import com.naveen.eclipse.gva.personalization.PersonalizedStatistics;

/**
 * COMPONENT - Architecture, Module-level Interfaces 'Provided'
 * 
 * @author naveen naveen
 * @date 5/31/2015
 */
public interface IPersonalizationTopicEngine {

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
	//public static boolean notfiyTopicProcessEvent(PersonalizedTopicEvent pEvent);
	
	
	/**
	 * FUTURE: GitViz 2.0 will return relevant usage Statistics to calling Components (for
	 * application-level features), and also directly to end user.
	 * 
	 * @param uid
	 * @param pTopicCategory
	 * @param frequency
	 * @return PersonalizedStatistics
	 */
	public PersonalizedStatistics getUsagePatterns(
			String uid, 
			String pTopicCategory, 
			int frequency);

	/**
	 * FUTURE: GitViz 3.0 will return relevant Recommendations to calling Components (for
	 * application-level features), and also directly to end user.
	 * 
	 * This requires Machine Learning algorithm and Topic Modeling not realized in the current
	 * implementation, but envisioned in the current System Design.
	 * 
	 * @param pDataView
	 * @param pGitUser
	 * @return
	 */
	public PersonalizedStatistics personalizeRecommendation(
			DataViewComponent pDataView, 
			String pGitUser);

}
