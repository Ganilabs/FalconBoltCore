package com.ganilabs.falconbolt.core.View.components;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class TransparentButton extends JButton{
	Font textFont;
	public TransparentButton(String text , Color color) {
		super(text);
		this.initButton(color);
	}
	
	public TransparentButton(String text , ImageIcon icon , Color color) {
		super(text , icon);
		this.initButton(color);
	}
	public TransparentButton(String text , Font textFont , Color color) {
		super(text);
		this.textFont = textFont;
		this.initButton(color);
	}
	private void initButton(Color color) {
		setBackground(color);
    	setForeground(StyleConstants.FOREGROUND_PRIMARY);
    	setBorder(BorderFactory.createEmptyBorder());
		if(this.textFont != null){
			setFont(this.textFont);
		}else{
			setFont(StyleConstants.HEADING_SUB3);
		}
	}
}
