package com.ganilabs.falconbolt.core.View.modals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.ganilabs.falconbolt.core.Model.repository.project.ProjectDTO;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.components.Card;
import com.ganilabs.falconbolt.core.View.components.TransparentButton;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class OpenProjectModal extends AbstractModal{
	AbstractWorkView parentView;
	List<ProjectDTO> projects;
	public OpenProjectModal(AbstractWorkView parentView, String title , List<ProjectDTO> projects) {
		super(parentView, title);
		this.parentView = parentView;
		this.projects = projects;
	}
	
	@Override
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					createUI();
				}catch(Exception e) {
					parentView.update(Constant.ErrorMessages.RESOURCE_FAILED_TO_LOAD);
				}
			}
		});
	}
	
	private void createUI() throws IOException{
		JPanel mainPanel = new JPanel( new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		BoxLayout layout = new BoxLayout(contentPanel , BoxLayout.Y_AXIS);
		contentPanel.setLayout(layout);
		JLabel heading = new JLabel(DisplayTextResources.OPEN_PROJECT);
		heading.setFont(StyleConstants.HEADING_SUB1);
		heading.setForeground(StyleConstants.FOREGROUND_PRIMARY);
		heading.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(Box.createVerticalStrut(20));
		contentPanel.add(heading);
		contentPanel.add(Box.createVerticalStrut(20));
		for(ProjectDTO project : this.projects) {
			contentPanel.add(createCardForProject(project));
			contentPanel.add(Box.createVerticalStrut(7));
		}
		JPanel westPanel = new JPanel();
		westPanel.setSize(50 , 600);
		westPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);
		mainPanel.add(westPanel , BorderLayout.WEST);
		mainPanel.add(contentPanel , BorderLayout.CENTER);
		this.setContentPane(mainPanel);
		this.setSize(600 , 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	private Card createCardForProject(ProjectDTO project) throws IOException {
		JPanel cardContent = new JPanel(new FlowLayout());
		cardContent.setBackground(StyleConstants.BACKGROUND_SECONDARY);
		BufferedImage lightningIconBuff = ImageIO.read(this.getClass().getResource("lightning.png"));
    	ImageIcon lightningIcon = new ImageIcon(lightningIconBuff);
    	Image scaledLightningImage = lightningIcon.getImage().getScaledInstance(20 , -1, Image.SCALE_SMOOTH);
    	ImageIcon scaledLightningIcon = new ImageIcon(scaledLightningImage);
		JButton projectButton = new TransparentButton( project.getProjectName(),scaledLightningIcon , StyleConstants.BACKGROUND_SECONDARY);
		projectButton.setPreferredSize(new Dimension(350 , projectButton.getPreferredSize().height));
		projectButton.setHorizontalAlignment(SwingConstants.LEFT);
		cardContent.add(projectButton);
//		cardContent.add(Box.createHorizontalStrut(250));
		JLabel createdOn = new JLabel("Created On : " + project.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		createdOn.setFont(StyleConstants.NORMAL_TEXT);
		createdOn.setForeground(StyleConstants.FOREGROUND_SECONDARY);
		cardContent.add(createdOn);
		cardContent.setMaximumSize(createdOn.getSize());
		Card card = new Card(cardContent);
		card.setMaximumSize(new Dimension(550 , card.getPreferredSize().height));
		card.setAlignmentX(LEFT_ALIGNMENT);
		return card;
	}

}
