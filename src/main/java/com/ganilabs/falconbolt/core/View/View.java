package com.ganilabs.falconbolt.core.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.ganilabs.falconbolt.core.Control.Control;
import com.ganilabs.falconbolt.core.Control.viewHandlers.WelcomeViewController;
import com.ganilabs.falconbolt.core.Control.viewHandlers.WorkspaceViewController;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.repository.general.GeneralRepository;
import com.ganilabs.falconbolt.core.View.workViews.WelcomeWorkView;
import com.ganilabs.falconbolt.core.View.workViews.WorkspaceWorkView;
import com.ganilabs.falconbolt.core.constant.StyleConstants;

public class View{
    private static final Logger LOGGER = LogManager.getLogger(View.class);
    private Map<String , AbstractWorkView> loadedViews = new HashMap<String , AbstractWorkView>();
    private static View view;
    private Model model;
    private Control control;
    private MainFrame mainFrame = new MainFrame();
    private MainPanel mainPanel = new MainPanel(new BorderLayout());
    private StatusBarPanel statusBar;
    public static final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    private View(){
    }
    public static View getSingleton() throws IllegalStateException{
        if(view == null){
            throw new IllegalStateException("Singleton View has not been initialized");
        }
        return view;
    }

    public static View initSingleton(Model model, Control control){
        if(view != null){
            return view;
        }
        view = new View();
        view.model = model;
        view.control = control;
        return view;
    }

    public void init(){
    	this.setLookAndFeel();
    	this.loadWorkViews();
        this.initializeUI();
    }
    
    private void loadWorkViews() {
    	AbstractWorkView welcomeView = new WelcomeWorkView(new WelcomeViewController() , this.model);
    	this.mainPanel.loadWorkView(welcomeView);
    	view.loadedViews.put(welcomeView.getViewName(), welcomeView);
    	AbstractWorkView workspaceView = new WorkspaceWorkView(new WorkspaceViewController() , this.model);
    	this.mainPanel.loadWorkView(workspaceView);
    	view.loadedViews.put(workspaceView.getViewName(), workspaceView);
    }
    
    private void setLookAndFeel() {
    	UIManager.put("PopupMenu.border",
				BorderFactory.createMatteBorder(1,1, 1, 1 , StyleConstants.FOREGROUND_SECONDARY)
				);
    	UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder());
    	UIManager.put("SplitPane.border", BorderFactory.createEmptyBorder());
    	UIManager.put("SplitPane.dividerSize", 2);
        UIManager.put("SplitPaneDivider.border", BorderFactory.createLineBorder(StyleConstants.FOREGROUND_SECONDARY, 2));
//        UIManager.put("SplitPaneDivider.draggingColor", StyleConstants.FOREGROUND_SECONDARY);
    }

    public void setView (AbstractWorkView view){
    	try {
    		model.setLiveView(view);
            this.mainPanel.displayWorkView(view.getViewName());
    	}catch(Exception e) {
    		System.out.print(e.getMessage());
    		model.shutDownGracefully();
    	}
    	
    }
    
    public void setView(String viewName) {
    	this.setView(view.loadedViews.get(viewName));
    }

    public void chooseWorkView(){
    	Optional<GeneralRepository> repoGeneralOp = model.getGeneralRepository();
    	if(repoGeneralOp.isEmpty()) return;
    	GeneralRepository repoGeneral = repoGeneralOp.get();
    	Integer openedProjectId = repoGeneral.getOpenedProjectId();
    	if(openedProjectId == null)this.setView(WelcomeWorkView.VIEW_NAME);
    	else this.setView(WorkspaceWorkView.VIEW_NAME);
    }

    public void startApp(){
        try{
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainFrame.setVisible(true);
                }
            });
        }catch(Exception e){
            LOGGER.fatal(e.getMessage() , e);
            System.exit(1);
        }
    }
    
    public void closeOpenedProject() {
    	try {
    		Optional<GeneralRepository> repoOp = model.getGeneralRepository();
        	if(repoOp.isEmpty()) return;
        	GeneralRepository repo = repoOp.get();
        	repo.setOpenedProject(null);
        	this.setView(view.loadedViews.get(WelcomeWorkView.VIEW_NAME));
    	}catch(HibernateException e) {
    		LOGGER.error(e.getMessage() , e);
    	}
    	
    }

    private void initializeUI(){
        try{
            LOGGER.info("Initializing the UI");
            statusBar = new StatusBarPanel();
            mainFrame.init();
            mainFrame.setContentPane(new JPanel(new BorderLayout()));
            mainFrame.getContentPane().add(mainPanel , BorderLayout.CENTER);
            mainFrame.getContentPane().add(statusBar , BorderLayout.SOUTH);
            mainFrame.pack();
            mainFrame.addWindowListener(new WindowAdapter() {
            	@Override
            	public void windowClosing(WindowEvent e) {
            		model.shutDownGracefully();
            	}
            });
        } catch (Exception e){
            LOGGER.fatal(e.getMessage() , e);
            System.exit(1);
        }
    }
}
