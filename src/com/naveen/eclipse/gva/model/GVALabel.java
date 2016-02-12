package com.naveen.eclipse.gva.model;

/**
 * Model Class per Tech Design - Extends Git 'Label' data
 * 
 * @author naveen naveen
 * @date 05/16/2015
 */
public class GVALabel {
	
	private String mName;
	private String mDescription;
	private String mColor;
	private String mMyTopicalMeaning;

	
	/** 
	 * Retrieves a label’s name e.g. bug, feature request etc.
	 * @param name
	 */
	protected void setName(String name) {
		mName = name;
	}
	/**
	 * @return String
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * @param description
	 */
	public void setDescription(String description) {
		mDescription = description;
	}
	/**
	 * 		Retrieves a label’s description from its url page. 
	 * (Labels descriptions are defined in the page hosted at their url)
	 * @return string
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * @param color
	 */
	protected void setColor(String color) {
		mColor = color;
	}
	/**
	 * @return
	 */
	public String getColor() {
		return mColor;
	}
	
	/**
	 * @return
	 */
	public String getMyTopicalMeaning() {
		return mMyTopicalMeaning;
	}
	/**
	 * Retrieve the optional semantic meaning that a user may have used to describe 
	 * a ‘Label’ prior to applying one in GitHub.com source code. 
	 * Used by Personalization Topic Model Engine.
	 * 
	 * @param myTopicalMeaning
	 */
	protected void setMyTopicalMeaning(String myTopicalMeaning) {
		mMyTopicalMeaning = myTopicalMeaning;
	}

}
