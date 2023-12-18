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
import com.ganilabs.falconbolt.core.View.ChildFrameListener;
import com.ganilabs.falconbolt.core.View.View;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.ComponentHelper;
import com.ganilabs.falconbolt.core.View.components.MenuBar;
import com.ganilabs.falconbolt.core.View.components.SelectedToolPopupMenu;
import com.ganilabs.falconbolt.core.View.components.TransparentButton;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;
import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;
import com.ganilabs.falconbolt.interfaces.pluginmessages.ScanResultMessage;

public class WorkspaceWorkView extends AbstractWorkView{
	public final static String VIEW_NAME = "Workspace View";
	private WorkspaceViewController controller;
	private JPanel leftToolBar = new JPanel();
	private JPanel rightTasksBar = new JPanel();
	private JPanel workPanel = new JPanel(new BorderLayout());

	private JPanel openedToolsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private CardLayout toolLayout = new CardLayout();
	private JPanel toolContentPanel = new JPanel(toolLayout);
	private JSplitPane splitPane;
	private Dimension screenDimension = View.screenDim;

	private WorkspaceWorkView thisReference = this;

	public WorkspaceWorkView(WorkspaceViewController controller, Model model) {
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
			case Constant.ToolMessages.CLOSE_SELECTED_TOOL:
				controller.closeSelectedTool((AbstractTool)message.getMsgData());
		}

	}

	@Override
	public void update(String msg) {
		super.update(msg);
		switch(msg){
			case Constant.ToolMessages.OPEN_CLOSED_TOOL:
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						renderOpenedToolsBar();
					}
				});
				break;
			case Constant.ToolMessages.CLOSE_SELECTED_TOOL:
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						renderOpenedToolsBar();
						toolContentPanel.removeAll();
						toolContentPanel.add(prepareDefaultTool());
						toolContentPanel.repaint();
					}
				});
				break;
			case Constant.ToolMessages.SCAN_RESULT_CHANGE:
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

					}
				});
		}
	}


	public void update(String msg, String helperMsg) {

	}

	private void createUI() {
		this.setLayout(new BorderLayout());
		this.initSplitPane();
		this.add(splitPane, BorderLayout.CENTER);
		this.add(new MenuBar(this), BorderLayout.NORTH);

	}

	private void initSplitPane() {
		this.initWorkPanel();
		this.initLeftToolBar();
		this.initRightTasksBar();
		JSplitPane nestedPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.workPanel, this.rightTasksBar);
		nestedPane.setBorder(null);
		nestedPane.setOneTouchExpandable(true);
		nestedPane.setDividerLocation((int) (0.60 * this.screenDimension.width));
		JPanel rightContainer = new JPanel(new BorderLayout());
		rightContainer.add(nestedPane, BorderLayout.CENTER);
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.leftToolBar, rightContainer);
		this.splitPane.setDividerLocation((int) (0.20 * this.screenDimension.width));
		this.splitPane.setBorder(null);
		this.splitPane.setOneTouchExpandable(true);
		this.add(this.splitPane, BorderLayout.CENTER);
	}


	private void initWorkPanel() {
		this.renderOpenedToolsBar();
		this.toolContentPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		this.toolContentPanel.add(prepareDefaultTool(), "DEFAULT");
		this.workPanel.add(openedToolsPanel, BorderLayout.NORTH);
		this.workPanel.add(this.toolContentPanel, BorderLayout.CENTER);
		toolLayout.show(toolContentPanel, "DEFAULT");
	}

	private void renderOpenedToolsBar() {
		openedToolsPanel.removeAll();
		openedToolsPanel.revalidate();
		Optional<OpenedTools> openedPluginsOp = model.getOpenedTools();
		if (openedPluginsOp.isEmpty()) return;
		OpenedTools openedTools = openedPluginsOp.get();
		for (AbstractTool tool : openedTools.getAllOpenedTools()) {
			openedToolsPanel.add(createButtonForOpenedTool(tool));
			openedToolsPanel.add(Box.createHorizontalStrut(5));
		}
		openedToolsPanel.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		openedToolsPanel.setPreferredSize(new Dimension(openedToolsPanel.getPreferredSize().width, 30));
		openedToolsPanel.repaint();
	}
	private void renderOpenedTool(AbstractTool tool) {
		this.toolContentPanel.removeAll();
		this.toolContentPanel.add(tool.getToolUI() , tool.getToolName());
		this.toolLayout.show(this.toolContentPanel , tool.getToolName());
		this.toolContentPanel.repaint();
	}

	private JPanel prepareDefaultTool() {
		JPanel defaultPanel = new JPanel();
		JLabel welcomeNote = new JLabel(DisplayTextResources.WELCOME_TEXT);
		defaultPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		welcomeNote.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		welcomeNote.setFont(StyleConstants.HEADING_SUB3);
		defaultPanel.add(welcomeNote);
		return defaultPanel;
	}

	private void initLeftToolBar() {
		Optional<ToolsStore> toolsStoreOp = model.getToolsStore();
		if (toolsStoreOp.isEmpty()) return;
		ToolsStore toolsStore = toolsStoreOp.get();
		Map<String, AbstractTool> tools = toolsStore.getAllLoadedTools();
		BoxLayout layout = new BoxLayout(this.leftToolBar, BoxLayout.Y_AXIS);

		Set<String> toolsKeys = tools.keySet();
		JLabel toolsHeading = new JLabel(DisplayTextResources.TOOLBOX_HEADING);
		toolsHeading.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		toolsHeading.setFont(StyleConstants.HEADING_SUB3);
		toolsHeading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		toolsHeading.setAlignmentX(CENTER_ALIGNMENT);
		this.leftToolBar.add(toolsHeading);
		for (String key : toolsKeys) {
			AbstractTool tool = tools.get(key);
			this.leftToolBar.add(this.createButtonForTool(tool));
		}
		this.leftToolBar.setLayout(layout);
		this.leftToolBar.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		this.leftToolBar.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.leftToolBar.setMaximumSize(new Dimension((int) 0.3 * this.screenDimension.width, this.screenDimension.height));
	}

	private TransparentButton createButtonForTool(AbstractTool tool) {
		ImageIcon scaledNewIcon = ComponentHelper.resizeImageIconToSize(tool.getToolIcon(), (int) (this.screenDimension.width * 0.020));
		TransparentButton button = new TransparentButton(tool.getToolName(), scaledNewIcon, StyleConstants.BACKGROUND_SECONDARY);
		button.setFont(StyleConstants.NORMAL_TEXT);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				controller.openSelectedNewTool(tool);
				renderOpenedTool(tool);
			}
		});
		return button;
	}

	private void initRightTasksBar() {
		this.rightTasksBar.removeAll();
		this.rightTasksBar.revalidate();
		Set<ScanResultMessage> scanResults= model.getScanResultMessages();
		this.rightTasksBar.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		this.rightTasksBar.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.rightTasksBar.setMaximumSize(new Dimension((int) 0.3 * this.screenDimension.width, this.screenDimension.height));
	}

	private TransparentButton createButtonForOpenedTool(AbstractTool tool) {
		ImageIcon icon = tool.getToolIcon();
		TransparentButton button = new TransparentButton(
				tool.getToolName(),
				ComponentHelper.resizeImageIconToSize(icon, (int) (this.screenDimension.width * 0.015)),
				StyleConstants.BACKGROUND_TERTIARY
		);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					renderOpenedTool(tool);
				}else{
					System.out.println("Hit 1");
					SelectedToolPopupMenu popupMenu = new SelectedToolPopupMenu(tool , thisReference);
					popupMenu.show(e.getComponent() , e.getX() , e.getY());
				}
			}
		});
		button.setFont(StyleConstants.SMALL_TEXT);
		button.setBorder(BorderFactory.createEmptyBorder(2 , 4 , 2 , 4));
		return button;
	}

}
