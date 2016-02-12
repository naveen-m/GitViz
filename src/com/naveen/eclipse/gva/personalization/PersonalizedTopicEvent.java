package com.naveen.eclipse.gva.personalization;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @author dd3152
 */
public class PersonalizedTopicEvent extends org.eclipse.swt.widgets.Event {

	private String mUserId;
	
	private Date mEventTimeStamp;

	private String mTopicName;
	
	private Map mTopicMapTokens;
 	
	/**
	 * 
	 * 
	 * @param pUserId
	 * @param pEventTimeStamp
	 * @param pTopicName
	 * @param pTopicMapTokens
	 */
	public PersonalizedTopicEvent(
			final String pUserId,
			final Date pEventTimeStamp,
			final String pTopicName,
			final Map pTopicMapTokens
			) {
		mUserId = pUserId;
		mEventTimeStamp = pEventTimeStamp;
		mTopicName = pTopicName;
		mTopicMapTokens = pTopicMapTokens;
	}
	
}
