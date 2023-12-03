package com.ganilabs.falconbolt.core.View.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class TransparentButton extends JButton{
	public TransparentButton(String text , Color color) {
		super(text);
		this.initButton(color);
	}
	
	public TransparentButton(String text , ImageIcon icon , Color color) {
		super(text , icon);
		this.initButton(color);
	}
	private void initButton(Color color) {
		setBackground(color);
    	setForeground(StyleConstants.FOREGROUND_PRIMARY);
    	setBorder(BorderFactory.createEmptyBorder());
    	setFont(StyleConstants.HEADING_SUB3);
	}
}
