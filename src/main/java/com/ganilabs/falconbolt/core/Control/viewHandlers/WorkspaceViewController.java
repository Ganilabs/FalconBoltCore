package com.ganilabs.falconbolt.core.Control.viewHandlers;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.tools.AbstractTool;
import com.ganilabs.falconbolt.core.Model.tools.OpenedTools;
import com.ganilabs.falconbolt.core.View.View;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.interfaces.plugin.PluginMessage;
import com.ganilabs.falconbolt.interfaces.plugin.PluginMessageListener;
import com.ganilabs.falconbolt.interfaces.pluginmessages.ScanResultMessage;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

import java.util.Optional;

public class WorkspaceViewController{
	Model model = Model.getSingleton();
	public void closeOpenedProject() {
		View.getSingleton().closeOpenedProject();
	}
	public void openSelectedNewTool(AbstractTool tool){
		model.getOpenedTools().get().addNewOpenedTool(tool);
		model.notifyObservers(Constant.ToolMessages.OPEN_CLOSED_TOOL);
	}

	public void closeSelectedTool(AbstractTool tool){
		Optional<OpenedTools> openedToolsOptional = model.getOpenedTools();
		if(openedToolsOptional.isEmpty()) return;
		OpenedTools openedTools = openedToolsOptional.get();
		openedTools.closeTool(tool);
		model.notifyObservers(Constant.ToolMessages.CLOSE_SELECTED_TOOL);
	}

	public void addNewResultsEntry(ScanResultMessage result){
		model.addScanResultsMessage(result);
		model.notifyObservers(Constant.ToolMessages.SCAN_RESULT_CHANGE);
	}
}
