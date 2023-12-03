package com.ganilabs.falconbolt.core.View.components;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class NormalTextField extends JTextField{
	public NormalTextField() {
		super();
		this.initTextField();
	}
	private void initTextField() {
		setBackground(StyleConstants.BACKGROUND_SECONDARY);
		setForeground(StyleConstants.FOREGROUND_SECONDARY);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, StyleConstants.FOREGROUND_SECONDARY));
	}
}
