package com.ganilabs.falconbolt.core.View.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class Card extends JPanel{
	private JPanel content;
	public Card(JPanel content) {
		super();
		setBackground(StyleConstants.BACKGROUND_SECONDARY);
		setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));
		setForeground(StyleConstants.FOREGROUND_PRIMARY);
		this.content = content;
		add(content , BorderLayout.CENTER);
	}
}
