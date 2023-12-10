package com.ganilabs.falconbolt.core.View.workViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.ganilabs.falconbolt.core.View.components.Card;
import com.ganilabs.falconbolt.core.View.components.TransparentButton;
import com.ganilabs.falconbolt.core.View.modals.AbstractModal;
import com.ganilabs.falconbolt.core.View.modals.NewProjectModal;
import com.ganilabs.falconbolt.core.View.modals.OpenProjectModal;
import com.ganilabs.falconbolt.core.View.modals.ProjectPopUpMenu;
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
    private JPanel recentsPanel;
    
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
    
    private void createUI(){
    	try {
    		this.setLayout(new GridLayout(0 , 2));
        	this.setPreferredSize(new Dimension((int)(0.8 * this.screenDim.width) , (int)(0.8 * this.screenDim.height)));
        	recentsPanel = new JPanel();
        	recentsPanel.setLayout(new BoxLayout(recentsPanel , BoxLayout.Y_AXIS));
        	final JPanel projectOptionPanel = new JPanel();
        	this.setupProjectOptionPanel(projectOptionPanel);
        	
        	SwingUtilities.invokeLater(new Runnable(){
    			@Override
    			public void run() {
    				try {
        				setupRecentPanel();
    				}catch(IOException e) {
    					LOGGER.error("Failed to load RecentProjectPanel" , e);
    					model.externalNotifyLiveView(Constant.ErrorMessages.RESOURCE_FAILED_TO_LOAD);
    				}
    				
    			}
    		});
        	
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
    
    private void setupRecentPanel() throws IOException {
    	List<ProjectDTO> recentProjectsList = controller.getAllProjectsSortedByOpeningTime(15);
    	recentsPanel.setBackground(StyleConstants.BACKGROUND_SECONDARY);
    	JLabel heading = new JLabel(DisplayTextResources.RECENT_PROJECTS);
    	heading.setFont(StyleConstants.HEADING_SUB2);
    	heading.setForeground(StyleConstants.FOREGROUND_TERTIARY);
    	heading.setBorder(BorderFactory.createEmptyBorder(0 , 20 , 0 , 20));
    	recentsPanel.add(Box.createVerticalStrut(20));
    	recentsPanel.add(heading);
    	recentsPanel.add(Box.createVerticalStrut(20));
    	
    	//Projects displayed
    	for(ProjectDTO project : recentProjectsList) {
			recentsPanel.add(createCardForProject(project));
			recentsPanel.add(Box.createVerticalStrut(7));
		}
    }
    
    @Override
    public String getViewName() {
    	return VIEW_NAME;
    }

    @Override
    public void update(String msg) {
    	super.update(msg);
    	switch(msg) {
    	case Constant.ModelChangeMessages.PROJECT_CRUD:
    		SwingUtilities.invokeLater(new Runnable() {
    			@Override
    			public void run() {
    				recentsPanel.removeAll();
    				try {
    					recentsPanel.revalidate();
						setupRecentPanel();
						recentsPanel.repaint();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		});
    		
    	}
    }
    
    //used to receive data from dialogs and popup menus.
    @Override
    public void captureEventFromChildSubFrame(ViewMessage msg) {
    	switch(msg.getMsgType()) {
    	case Constant.ViewMessages.NEW_PROJECT_NAME:
    		LOGGER.info("Creating new project {} " , msg.getMsgData());
    		controller.createNewProject((String)msg.getMsgData());
    		break;
    	case Constant.ViewMessages.OPEN_SELECTED_PROJECT:
    		LOGGER.info("Opening project {}" , msg.getMsgData());
    		controller.openSelectedProject(WorkspaceWorkView.VIEW_NAME ,(Integer) msg.getMsgData());
    		break;
    	case Constant.ViewMessages.DELETE_SELECTED_PROJECT:
    		LOGGER.info("Deleting project {}" , msg.getMsgData());
    		controller.deleteProjectByProjectId((Integer)msg.getMsgData());
    		JOptionPane.showMessageDialog(new JFrame(), "Deleted Project", "Dialog",
    		        JOptionPane.INFORMATION_MESSAGE);
    		break;
    	}
    	
    		
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
		projectButton.setBorder(BorderFactory.createEmptyBorder(0 , 10 , 0 , 10));
		projectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					LOGGER.info("Opening project {}" , project.getProjectId());
					controller.openSelectedProject(WorkspaceWorkView.VIEW_NAME , project.getProjectId());
				}else {
					ProjectPopUpMenu popUp = new ProjectPopUpMenu(project.getProjectId() , thisReference);
					popUp.show(e.getComponent() , e.getX() , e.getY());
				}
			}
		});
		cardContent.add(projectButton);
//		cardContent.add(Box.createHorizontalStrut(250));
		JLabel createdOn = new JLabel("Opened On : " + project.getOpenedAt().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")));
		createdOn.setFont(StyleConstants.NORMAL_TEXT);
		createdOn.setForeground(StyleConstants.FOREGROUND_SECONDARY);
		cardContent.add(createdOn);
		cardContent.setMaximumSize(createdOn.getSize());
		Card card = new Card(cardContent);
		card.setMaximumSize(new Dimension((int)(screenDim.width * 0.45) , card.getPreferredSize().height));
		card.setAlignmentX(LEFT_ALIGNMENT);
		return card;
	}
}
