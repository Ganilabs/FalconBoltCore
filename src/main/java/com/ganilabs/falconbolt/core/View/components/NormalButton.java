package com.ganilabs.falconbolt.core.View.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class NormalButton extends JButton{
	public NormalButton(String text) {
		super(text);
		this.initButton();
	}
	
	public NormalButton(String text , ImageIcon icon) {
		super(text , icon);
		this.initButton();
	}
	
	private void initButton() {
		setBackground(StyleConstants.BACKGROUND_SECONDARY);
    	setForeground(StyleConstants.FOREGROUND_PRIMARY);
    	setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleConstants.BACKGROUND_TERTIARY, 1),
                BorderFactory.createMatteBorder(5 , 20 , 5 , 20 , StyleConstants.BACKGROUND_SECONDARY)));
    	setFont(StyleConstants.NORMAL_TEXT);
	}
}
