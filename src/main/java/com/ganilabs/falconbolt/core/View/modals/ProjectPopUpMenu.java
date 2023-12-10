package com.ganilabs.falconbolt.core.View.modals;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.ganilabs.falconbolt.core.View.ChildFrameListener;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.AbstractPopUpMenu;
import com.ganilabs.falconbolt.core.View.components.MenuItem;
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
		JMenuItem openItem = new MenuItem(DisplayTextResources.OPEN);
		openItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				parent.captureEventFromChildSubFrame(new ViewMessage(Constant.ViewMessages.OPEN_SELECTED_PROJECT , projectId));
			}
		});
		
		JMenuItem deleteItem = new MenuItem(DisplayTextResources.DELETE);
		deleteItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(
		                null,
		                "Are you sure you want to delete the project?",
		                "Confirmation",
		                JOptionPane.YES_NO_OPTION
		        );
		        if (result == JOptionPane.YES_OPTION) {
		        	parent.captureEventFromChildSubFrame(new ViewMessage(Constant.ViewMessages.DELETE_SELECTED_PROJECT , projectId));
		        }
			}
		});
		this.add(openItem);
		this.add(deleteItem);
	}
}
