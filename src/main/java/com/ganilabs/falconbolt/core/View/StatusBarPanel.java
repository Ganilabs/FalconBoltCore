package com.ganilabs.falconbolt.core.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class StatusBarPanel extends JPanel {
	private Dimension screenDim = View.getSingleton().screenDim; 
	StatusBarPanel() {
		this.setBackground(new Color(70 , 70 , 70));
		this.setPreferredSize(new Dimension(screenDim.width , (int)(0.04 * screenDim.height)));
	}
}
