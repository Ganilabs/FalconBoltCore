package com.ganilabs.falconbolt.core.View.modals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.NormalButton;
import com.ganilabs.falconbolt.core.View.components.NormalTextField;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class NewProjectModal extends AbstractModal{
	AbstractWorkView parentView;
	public NewProjectModal(AbstractWorkView parentView) {
		super(parentView , DisplayTextResources.NEW_PROJECT);
		this.parentView = parentView;
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
		JLabel sessionNameLabel = new JLabel(DisplayTextResources.NEW_PROJECT_NAME);
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
		
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String projectName = sessionNameTB.getText();
				if(!isNameValidFormat(projectName)) {
					JOptionPane.showMessageDialog(new JFrame(), "Invalid Input : Should contain no specials chars and must start with an alphabet", "Dialog",
		    		        JOptionPane.ERROR_MESSAGE);
				}else {
					 dispose();
					 parentView.captureEventFromChildSubFrame(new ViewMessage(Constant.ViewMessages.NEW_PROJECT_NAME , projectName));
				}
			}
		});
		this.setContentPane(mainPanel);
		this.setSize(600 , 600);
		this.setVisible(true);
		
	}
	private Boolean isNameValidFormat(String name) {
		if(name.length() == 0) return false;
		//contains no specials chars and starts with alphabet
		Pattern pattern = Pattern.compile("^[a-z]+[0-9]*[a-z]*$" , Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(name);
		if(!matcher.find()) return false;
		return true;
		
	}
}
