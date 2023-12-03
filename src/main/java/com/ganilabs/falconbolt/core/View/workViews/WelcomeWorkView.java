package com.ganilabs.falconbolt.core.View.workViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ganilabs.falconbolt.core.Control.viewHandlers.WelcomeViewController;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectDTO;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.View;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.View.components.TransparentButton;
import com.ganilabs.falconbolt.core.View.modals.AbstractModal;
import com.ganilabs.falconbolt.core.View.modals.NewProjectModal;
import com.ganilabs.falconbolt.core.View.modals.OpenProjectModal;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class WelcomeWorkView extends AbstractWorkView{
    public final static String VIEW_NAME = "WELCOME_VIEW";
    public final static Integer VIEW_ID = 1;
    private final static Logger LOGGER = LogManager.getLogger(WelcomeWorkView.class);
    private WelcomeViewController controller;
    private WelcomeWorkView thisReference = this;
    private Dimension screenDim = View.screenDim;
    public WelcomeWorkView(WelcomeViewController controller , Model model){
    	super(model);
    	//controller is separated into sub controller for every work view.
    	this.controller = controller;
    	try {
    		SwingUtilities.invokeAndWait(new Runnable() {
    			@Override
    			public void run() {
    				createUI();
    			}
    		});
    	}catch(Exception e) {
    		this.model.shutDownGracefully();
    		this.model.externalNotifyLiveView(Constant.ErrorMessages.ERROR_ENCOUNTERED);
    	}
    }
    
    private void createUI() {
    	try {
    		this.setLayout(new GridLayout(0 , 2));
        	this.setPreferredSize(new Dimension((int)(0.8 * this.screenDim.width) , (int)(0.8 * this.screenDim.height)));
        	final JPanel recentsPanel = new JPanel();
        	recentsPanel.setLayout(new BoxLayout(recentsPanel , BoxLayout.Y_AXIS));
        	final JPanel projectOptionPanel = new JPanel();
        	this.setupProjectOptionPanel(projectOptionPanel);
        	this.setupRecentPanel(recentsPanel);
        	this.add(recentsPanel);
            this.add(projectOptionPanel);
    	}catch(IOException e) {
    		LOGGER.error(e.getMessage() , e);
    		model.externalNotifyLiveView(Constant.ErrorMessages.RESOURCE_FAILED_TO_LOAD);
    	}
    	
        	
    }
    
    private void setupProjectOptionPanel(JPanel projectOptionPanel) throws IOException{
    	projectOptionPanel.setLayout(new BoxLayout(projectOptionPanel , BoxLayout.Y_AXIS));
    	projectOptionPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);	
    	//Setup logo
    	BufferedImage logo = ImageIO.read(this.getClass().getResource("welcome-logo.png"));
    	ImageIcon logoIcon = new ImageIcon(logo);
    	Image scaledLogoImage = logoIcon.getImage().getScaledInstance((int) (screenDim.height * 0.2), -1, Image.SCALE_SMOOTH);
    	ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
    	JLabel labelLogo = new JLabel(scaledLogoIcon);
    	labelLogo.setAlignmentX(CENTER_ALIGNMENT);
    	labelLogo.setBorder(new EmptyBorder(20 , 20 , 5 , 20));
    	//setup Product name
    	JLabel labelProductName = new JLabel(DisplayTextResources.PRODUCT_NAME);
    	labelProductName.setFont(StyleConstants.HEADING_SUB1);
    	labelProductName.setForeground(StyleConstants.FOREGROUND_PRIMARY);
    	JLabel labelProductVersion = new JLabel("V" + DisplayTextResources.VERSION);
    	labelProductVersion.setFont(StyleConstants.NORMAL_TEXT);
    	labelProductVersion.setForeground(Color.white);
    	JPanel bannerLabels = new JPanel(new FlowLayout());
    	bannerLabels.setBackground(StyleConstants.BACKGROUND_PRIMARY);
    	bannerLabels.add(labelProductName);
    	bannerLabels.add(labelProductVersion);
    	bannerLabels.setMaximumSize(bannerLabels.getPreferredSize());
    	bannerLabels.setAlignmentX(CENTER_ALIGNMENT);
    	
    	//New Project button
    	BufferedImage newIconBuff = ImageIO.read(this.getClass().getResource("plus.png"));
    	ImageIcon newIcon = new ImageIcon(newIconBuff);
    	Image scaledNewImage = newIcon.getImage().getScaledInstance((int) (screenDim.height * 0.035), -1, Image.SCALE_SMOOTH);
    	ImageIcon scaledNewIcon = new ImageIcon(scaledNewImage);
    	JButton newProjectButton = new TransparentButton("  " + DisplayTextResources.NEW_PROJECT , scaledNewIcon , StyleConstants.BACKGROUND_PRIMARY);
    	newProjectButton.setHorizontalTextPosition(SwingConstants.RIGHT);
    	newProjectButton.setVerticalTextPosition(SwingConstants.CENTER);
    	newProjectButton.setAlignmentX(CENTER_ALIGNMENT);
    	newProjectButton.setMargin(new Insets(20 , 20, 20 , 20));
    	
    	newProjectButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    			AbstractModal newProject = new NewProjectModal(thisReference);
    			newProject.init();
    		}
    	});
    	
    	//Open project button
    	BufferedImage openIconBuff = ImageIO.read(this.getClass().getResource("open.png"));
    	ImageIcon openIcon = new ImageIcon(openIconBuff);
    	Image scaledOpenImage = openIcon.getImage().getScaledInstance((int) (screenDim.height * 0.035), -1, Image.SCALE_SMOOTH);
    	ImageIcon scaledOpenIcon = new ImageIcon(scaledOpenImage);
    	JButton openProjectButton = new TransparentButton("  " + DisplayTextResources.OPEN_PROJECT , scaledOpenIcon , StyleConstants.BACKGROUND_PRIMARY);
    	openProjectButton.setHorizontalTextPosition(SwingConstants.RIGHT);
    	openProjectButton.setVerticalTextPosition(SwingConstants.CENTER);
    	openProjectButton.setAlignmentX(CENTER_ALIGNMENT);
    	openProjectButton.setMargin(new Insets(20 , 20, 20 , 20));
    	openProjectButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			SwingUtilities.invokeLater(new Runnable() {
    				@Override
    				public void run() {
    					List<ProjectDTO> projectList = controller.getAllProjects();
    					AbstractModal openProject = new OpenProjectModal(thisReference , DisplayTextResources.OPEN_PROJECT , projectList);
    	    			openProject.init();
    				}
    			});
    		}
    	});
    	
    	projectOptionPanel.add(labelLogo);  
    	projectOptionPanel.add(bannerLabels);
    	projectOptionPanel.add(Box.createVerticalStrut(40));
    	projectOptionPanel.add(newProjectButton);
    	projectOptionPanel.add(Box.createVerticalStrut(20));
    	projectOptionPanel.add(openProjectButton);
    	
    }
    
    private void setupRecentPanel(JPanel recentPanel) {
    	recentPanel.setBackground(StyleConstants.BACKGROUND_SECONDARY);
    }
    
    @Override
    public String getViewName() {
    	return VIEW_NAME;
    }

    @Override
    public void update(String msg) {
    	super.update(msg);
    }
    
    //used to receive data from dialogs.
    @Override
    public void captureEventFromChildSubFrame(ViewMessage msg) {
    	switch(msg.getMsgType()) {
    	case Constant.ViewMessages.NEW_PROJECT_NAME:
    		LOGGER.info("Creating new project {} " , msg.getMsgData());
    		controller.createNewProject((String)msg.getMsgData());
    	}
    }
}
