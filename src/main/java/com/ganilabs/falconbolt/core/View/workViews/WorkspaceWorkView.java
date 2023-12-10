package com.ganilabs.falconbolt.core.View.workViews;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.ganilabs.falconbolt.core.Control.viewHandlers.WorkspaceViewController;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.plugin.PluginStore;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.View;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.MenuBar;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.StyleConstants;
import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

public class WorkspaceWorkView extends AbstractWorkView{
	public final static String VIEW_NAME = "Workspace View"; 
	private WorkspaceViewController controller;
	private JPanel leftToolBar = new JPanel();
	private JPanel rightTasksBar = new JPanel();
	private JPanel WorkPanel = new JPanel();
	private JSplitPane splitPane;
	private Dimension screenDimension = View.screenDim;
	public WorkspaceWorkView(WorkspaceViewController controller , Model model) {
		super(model);
		this.controller = controller;
		this.createUI();
	}

	@Override
	public String getViewName() {
		return VIEW_NAME;
	}

	@Override
	public void captureEventFromChildSubFrame(ViewMessage message) {
		switch (message.getMsgType()) {
		case Constant.ViewMessages.CLOSE_OPENED_PROJECT:
			controller.closeOpenedProject();
			break;
		}
		
	}
	
	@Override
	public void update(String msg) {
		super.update(msg);
	}
	public void update(String msg , String helperMsg) {

	}
	
	private void createUI() {
		this.setLayout(new BorderLayout());
		this.initSplitPane();
		this.add(splitPane , BorderLayout.CENTER);
		this.add(new MenuBar(this) , BorderLayout.NORTH);
		
	}
	
	private void initSplitPane() {
		this.initWorkPanel();
		this.initLeftToolBar();
		this.initRightTasksBar();
		JSplitPane nestedPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT , this.WorkPanel , this.rightTasksBar);
		nestedPane.setBorder(null);
		nestedPane.setOneTouchExpandable(true);
		nestedPane.setDividerLocation((int)(0.60 * this.screenDimension.width));
		JPanel rightContainer = new JPanel(new BorderLayout());
		rightContainer.add(nestedPane , BorderLayout.CENTER);
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.leftToolBar , rightContainer);
		this.splitPane.setDividerLocation((int)(0.20 * this.screenDimension.width));
		this.splitPane.setBorder(null);
		this.splitPane.setOneTouchExpandable(true);
		this.add(this.splitPane , BorderLayout.CENTER);
	}


	private void initWorkPanel() {
		this.WorkPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		JLabel label = new JLabel("Soetext");
		label.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.WorkPanel.add(label);
	}

	private void initLeftToolBar() {
		Optional<PluginStore> pluginStoreOp =  model.getPluginStore();
		if(pluginStoreOp.isEmpty()) return;
		PluginStore pluginStore = pluginStoreOp.get();
		Map<String , PluginAPI> plugins = pluginStore.getAllLoadedPlugins();
		BoxLayout layout = new BoxLayout(this.leftToolBar , BoxLayout.Y_AXIS);
		this.leftToolBar.setLayout(layout);
		this.leftToolBar.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		this.leftToolBar.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.leftToolBar.setMaximumSize(new Dimension((int) 0.3 * this.screenDimension.width , this.screenDimension.height));
		JLabel label = new JLabel("Somthingkdsjfhskdjskdjskdj");
		label.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.leftToolBar.add(label);
	}
	
	private void initRightTasksBar() {
		this.rightTasksBar.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		this.rightTasksBar.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.rightTasksBar.setMaximumSize(new Dimension((int) 0.3 * this.screenDimension.width , this.screenDimension.height));
		JLabel label = new JLabel("Somthingkdsjfhskdjskdjskdj");
		label.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.rightTasksBar.add(label);
	}
	
}
