package com.ganilabs.falconbolt.core.View.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.ganilabs.falconbolt.core.View.ChildFrameListener;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class MenuBar extends JMenuBar{
	private ChildFrameListener parent;
	public MenuBar(ChildFrameListener parent) {
		super();
		this.parent = parent;
		this.createMenus();
		this.setStyling();
	}
	
	private void createMenus() {
		JMenu projectMenu = new JMenu(DisplayTextResources.MENU_BAR_PROJECT);
		JMenu viewMenu = new JMenu(DisplayTextResources.MENU_BAR_VIEW);
		JMenu windowMenu = new JMenu(DisplayTextResources.MENU_BAR_WINDOW);
		JMenu helpMenu = new JMenu(DisplayTextResources.MENU_BAR_HELP);
		projectMenu.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		viewMenu.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		windowMenu.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		helpMenu.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		
		//project Menu
		MenuItem closeProject = new MenuItem(DisplayTextResources.MENU_ITEM_CLOSE_PROJECT);
		closeProject.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				parent.captureEventFromChildSubFrame(new ViewMessage(Constant.ViewMessages.CLOSE_OPENED_PROJECT , null));
			}
		});
		projectMenu.add(closeProject);
		this.add(projectMenu);
		this.add(viewMenu);
		this.add(windowMenu);
		this.add(helpMenu);
	}
	
	private void setStyling() {
		this.setBackground(StyleConstants.BACKGROUND_TERTIARY);
		this.setForeground(StyleConstants.FOREGROUND_SECONDARY);
		this.setBorder(BorderFactory.createEmptyBorder(3 , 10 , 3 , 10));
	}
}
