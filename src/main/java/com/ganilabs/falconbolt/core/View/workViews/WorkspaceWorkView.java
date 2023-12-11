package com.ganilabs.falconbolt.core.View.workViews;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.*;

import com.ganilabs.falconbolt.core.Control.viewHandlers.WorkspaceViewController;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.tools.AbstractTool;
import com.ganilabs.falconbolt.core.Model.tools.OpenedTools;
import com.ganilabs.falconbolt.core.Model.tools.ToolsStore;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.View;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.ComponentHelper;
import com.ganilabs.falconbolt.core.View.components.MenuBar;
import com.ganilabs.falconbolt.core.View.components.TransparentButton;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;
import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

public class WorkspaceWorkView extends AbstractWorkView{
	public final static String VIEW_NAME = "Workspace View"; 
	private WorkspaceViewController controller;
	private JPanel leftToolBar = new JPanel();
	private JPanel rightTasksBar = new JPanel();
	private JPanel workPanel = new JPanel(new BorderLayout());

	private JPanel openedToolsPanel = new JPanel(new FlowLayout());
	private CardLayout toolLayout = new CardLayout();
	private JPanel toolContentPanel = new JPanel(toolLayout);
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
		JSplitPane nestedPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT , this.workPanel, this.rightTasksBar);
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
		this.initOpenedPluginsBar();
		this.toolContentPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		this.toolContentPanel.add(prepareDefaultTool() , "DEFAULT");
		this.workPanel.add(openedToolsPanel , BorderLayout.NORTH);
		this.workPanel.add(this.toolContentPanel , BorderLayout.CENTER);
		toolLayout.show(toolContentPanel , "DEFAULT");
	}

	private void initOpenedPluginsBar() {
		Optional<OpenedTools> openedPluginsOp = model.getOpenedTools();
		if (openedPluginsOp.isEmpty()) return;
		OpenedTools openedTools = openedPluginsOp.get();

		openedToolsPanel.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		openedToolsPanel.setPreferredSize(new Dimension(openedToolsPanel.getPreferredSize().width, 30));
	}

	private JPanel prepareDefaultTool(){
		JPanel defaultPanel = new JPanel();
		JLabel welcomeNote = new JLabel(DisplayTextResources.WELCOME_TEXT);
		defaultPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		welcomeNote.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		welcomeNote.setFont(StyleConstants.HEADING_SUB3);
		defaultPanel.add(welcomeNote);
		return defaultPanel;
	}

	private void initLeftToolBar() {
		Optional<ToolsStore> toolsStoreOp =  model.getToolsStore();
		if(toolsStoreOp.isEmpty()) return;
		ToolsStore toolsStore = toolsStoreOp.get();
		Map<String , AbstractTool> tools = toolsStore.getAllLoadedTools();
		BoxLayout layout = new BoxLayout(this.leftToolBar , BoxLayout.Y_AXIS);
		
		Set<String> toolsKeys = tools.keySet();
		JLabel toolsHeading = new JLabel(DisplayTextResources.TOOLBOX_HEADING);
		toolsHeading.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		toolsHeading.setFont(StyleConstants.HEADING_SUB3);
		toolsHeading.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		toolsHeading.setAlignmentX(CENTER_ALIGNMENT);
		this.leftToolBar.add(toolsHeading);
		for(String key : toolsKeys) {
			AbstractTool tool = tools.get(key);
			this.leftToolBar.add(this.createButtonForTool(tool));
		}
		this.leftToolBar.setLayout(layout);
		this.leftToolBar.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		this.leftToolBar.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.leftToolBar.setMaximumSize(new Dimension((int) 0.3 * this.screenDimension.width , this.screenDimension.height));
	}
	
	private TransparentButton createButtonForTool(AbstractTool tool) {
		ImageIcon scaledNewIcon = ComponentHelper.resizeImageIconToSize(tool.getToolIcon() , (int)(this.screenDimension.width * 0.020));
		TransparentButton button = new TransparentButton(tool.getToolName() , scaledNewIcon , StyleConstants.BACKGROUND_SECONDARY);
		button.setFont(StyleConstants.NORMAL_TEXT);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				System.out.println("Clicked plugin" + tool.getToolName());
			}
		});
		return button;
	}
	
	private void initRightTasksBar() {
		this.rightTasksBar.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		this.rightTasksBar.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.rightTasksBar.setMaximumSize(new Dimension((int) 0.3 * this.screenDimension.width , this.screenDimension.height));
	}

	private TransparentButton createButtonForOpenedPlugin(AbstractTool tool){
		ImageIcon icon = tool.getToolIcon();
		TransparentButton button = new TransparentButton(
				tool.getToolName() ,
				ComponentHelper.resizeImageIconToSize(icon , (int)(this.screenDimension.width * 0.035)),
				StyleConstants.BACKGROUND_TERTIARY
		);
		button.setFont(StyleConstants.SMALL_TEXT);
		return button;
	}
}
