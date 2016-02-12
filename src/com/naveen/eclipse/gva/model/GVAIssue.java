package com.naveen.eclipse.gva.model;

import java.util.List;

/**
 * Model Class per Tech Design - Extends Git 'Issue' data
 * 
 * @author naveen naveen
 * @date 05/16/2015
 */
public class GVAIssue {

	private String mName;
	private String mContents;
	private String mState;
	private String mAssignee;
	//private String mMilestone;
	private List<GVALabel> mLabels;
	
	/**
	 * Returns the name of the issue.
	 * @return String 
	 */
	public String getName() {
		return mName;
	}
	/**
	 * Returns the description/body text of the issue.
	 * @return String 
	 */
	public String getContents() {
		return mContents;
	}
	/**
	 * Returns the state of the issue e.g. open, closed etc.
	 * @return String 
	 */
	public String getState() {
		return mState;
	}
	/**
	 * Returns the user that the issue is assigned to.
	 * @return String 
	 */
	public String getAssignee() {
		return mAssignee;
	}

	/**
	 * @future
	 */
	//public Milestone getMilestone() {
		//: Returns the milestone where the issue is scheduled.
	//}

	/**
	 * Returns a list of labels this issue is associated with.
	 * @return String 
	 */
	public List<GVALabel> getLabels() {
		return mLabels;		
	}

	/**
	 * @param pName
	 */
	protected void setmName(String pName) {
		this.mName = pName;
	}
	/**
	 * @param pContents
	 */
	protected void setmContents(String pContents) {
		this.mContents = pContents;
	}
	/**
	 * @param pState
	 */
	protected void setmState(String pState) {
		this.mState = pState;
	}
	/**
	 * @param pAssignee
	 */
	protected void setmAssignee(String pAssignee) {
		this.mAssignee = pAssignee;
	}
	/**
	 * @param pLabels
	 */
	protected void setmLabels(List<GVALabel> pLabels) {
		this.mLabels = pLabels;
	}

}
