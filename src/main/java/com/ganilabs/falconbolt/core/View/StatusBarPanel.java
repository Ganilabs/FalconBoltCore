package com.ganilabs.falconbolt.core.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class StatusBarPanel extends JPanel {
	private Dimension screenDim = View.getSingleton().screenDim; 
	StatusBarPanel() {
		this.setBackground(StyleConstants.BACKGROUND_TERTIARY);
		this.setPreferredSize(new Dimension(screenDim.width , (int)(0.04 * screenDim.height)));
	}
}
