package com.naveen.eclipse.gva.model;

import org.eclipse.jface.action.Action;

public class GVADoubleClickEventAction extends Action {

	//NOTE: Static Design
	//TODO: define it
	//TODO: use it from GitVizSaturdaySampleView.hookDoubleClickAction(line196) !
	//TODO: this GVADoubleClickEventAction has_a set of descriptive properties:
	//		1.) Temporal timestamp
	//		2.) GitUserID?  Or does it already know that?
	//		3.) Topic: GitRepositoryName
	//		4.) Topic: Filename
	//		5.) Topic: HistoryVersionID (Git UID commitID) - extra credit!!
	
	//NOTE: Runtime Design
	//TODO: think about ECLIPSE VIEW UI sending event GVADoubleClickEventAction to TopicModel P12N listener
	//TODO: think about TopicModel P12N listener "Register Listener callback" with our ECLIPSE VIEW
	//TODO: think about TopicModel P12N listener having some callback method that simply says "Got it. Event received!"

}
