package com.ganilabs.falconbolt.core.View.components;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;

import com.ganilabs.falconbolt.core.View.ChildFrameListener;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public abstract class AbstractPopUpMenu extends JPopupMenu{
	protected ChildFrameListener parent;
	protected AbstractPopUpMenu(ChildFrameListener parent) {
		super();
		this.parent = parent;
	}
	
	private void initPopUpMenu() {
		this.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		this.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.setFont(StyleConstants.NORMAL_TEXT);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5 , 10 , 5 ,10),
				BorderFactory.createMatteBorder(1,1, 1, 1 , StyleConstants.FOREGROUND_SECONDARY)
				));
		this.setBorder(BorderFactory.createEmptyBorder());
		
	}
}
