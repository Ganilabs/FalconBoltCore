package com.ganilabs.falconbolt.core.View.modals;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.ganilabs.falconbolt.core.View.ChildFrameListener;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.AbstractPopUpMenu;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class ProjectPopUpMenu extends AbstractPopUpMenu{
	private Integer projectId;
	public ProjectPopUpMenu(Integer projectId , ChildFrameListener parent) {
		super(parent);
		this.projectId = projectId;
		this.initMenuItem();
	}
	
	private void initMenuItem() {
		JMenuItem openItem = new JMenuItem(DisplayTextResources.OPEN);
		openItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse clicked");
			}
		});
		
		JMenuItem deleteItem = new JMenuItem(DisplayTextResources.DELETE);
		deleteItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(
		                null,
		                "Are you sure you want to delete the project?",
		                "Confirmation",
		                JOptionPane.YES_NO_OPTION
		        );

		        // Check the user's choice
		        if (result == JOptionPane.YES_OPTION) {
		        	parent.captureEventFromChildSubFrame(new ViewMessage(Constant.ViewMessages.DELETE_SELECTED_PROJECT , projectId));
		        }
			}
		});
		setMenuItemStyle(openItem);
		setMenuItemStyle(deleteItem);
		this.add(openItem);
		this.add(deleteItem);
	}
	
	private void setMenuItemStyle(JMenuItem item) {
		item.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		item.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		item.setFont(StyleConstants.NORMAL_TEXT);
		item.setBorder(BorderFactory.createEmptyBorder(3 , 20 , 3 , 20));
	}
	
	
}
