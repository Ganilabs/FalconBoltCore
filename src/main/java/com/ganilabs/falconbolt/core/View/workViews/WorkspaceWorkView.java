package com.ganilabs.falconbolt.core.View.workViews;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.ViewMessage;

public class WorkspaceWorkView extends AbstractWorkView implements ModelObserver{
	public final static String VIEW_NAME = "Workspace View"; 
	protected WorkspaceWorkView(Model model) {
		super(model);
		
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}

	@Override
	public void captureEventFromChildSubFrame(ViewMessage message) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(String msg) {
		super.update(msg);
	}
	public void update(String msg , String helperMsg) {

	}
	
	private void createUI() {
		
	}
	
}
