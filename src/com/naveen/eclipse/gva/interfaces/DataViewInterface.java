package com.naveen.eclipse.gva.interfaces;

//import org.archstudio.myx.fw.AbstractMyxSimpleBrick;

import com.naveen.eclipse.gva.model.GVADoubleClickEventAction;

// ArchStudio: public class DataViewComponent extends AbstractMyxSimpleBrick {
//				If we could get it to work ... that would be awesome!!!

public interface DataViewInterface {

	// 1. New TimeStamp
	// 2. New TopicName (Repository, File, Label, etc)
	
	public GVADoubleClickEventAction getGVADatAction();
	
}
