package com.ganilabs.falconbolt.core.View.modals;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.components.NormalButton;
import com.ganilabs.falconbolt.core.View.components.NormalTextField;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class NewProjectModal extends AbstractModal{
	public NewProjectModal(AbstractWorkView parentView) {
		super(parentView , DisplayTextResources.NEW_PROJECT);
	}

	@Override
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createUI();
			}
		});
	}
	
	private void createUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel contentPanel = new JPanel();
		BoxLayout layout = new BoxLayout(contentPanel , BoxLayout.Y_AXIS);
		contentPanel.setLayout(layout);
		contentPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		//heading
		JLabel heading = new JLabel(DisplayTextResources.NEW_PROJECT);
		heading.setFont(StyleConstants.HEADING_SUB1);
		heading.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		//session name
		JLabel sessionNameLabel = new JLabel(DisplayTextResources.NEW_SESSION_NAME);
		sessionNameLabel.setFont(StyleConstants.NORMAL_TEXT);
		sessionNameLabel.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		JTextField sessionNameTB = new NormalTextField();
		sessionNameTB.setMaximumSize(new Dimension(600 , 25));
		
		NormalButton submitButton = new NormalButton(DisplayTextResources.CREATE_TEXT);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(heading);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(sessionNameLabel);
		contentPanel.add(Box.createVerticalStrut(5));
		contentPanel.add(sessionNameTB);
		contentPanel.add(Box.createVerticalStrut(25));
		contentPanel.add(submitButton);
		mainPanel.add(contentPanel , BorderLayout.CENTER);
		JPanel leftBorder = new JPanel();
		leftBorder.setSize(200 , 600);
		leftBorder.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		mainPanel.add(leftBorder , BorderLayout.WEST);
		this.setContentPane(mainPanel);
		this.setSize(600 , 600);
		this.setVisible(true);
	}
}
