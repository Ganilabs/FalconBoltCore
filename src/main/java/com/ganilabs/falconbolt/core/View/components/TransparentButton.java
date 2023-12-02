package com.ganilabs.falconbolt.core.View.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class TransparentButton extends JButton{
	public TransparentButton(String text) {
		super(text);
		this.initButton();
	}
	
	public TransparentButton(String text , ImageIcon icon) {
		super(text , icon);
		this.initButton();
	}
	private void initButton() {
		setBackground(StyleConstants.BACKGROUND_PRIMARY);
    	setForeground(StyleConstants.FOREGROUND_PRIMARY);
    	setBorder(BorderFactory.createEmptyBorder());
    	setFont(StyleConstants.HEADING_SUB3);
	}
}
