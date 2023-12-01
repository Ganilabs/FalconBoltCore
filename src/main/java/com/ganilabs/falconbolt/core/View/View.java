package com.ganilabs.falconbolt.core.View;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.Control.Control;
import com.ganilabs.falconbolt.core.Control.viewHandlers.WelcomeViewController;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;
import com.ganilabs.falconbolt.core.View.workViews.WelcomeWorkView;
import com.ganilabs.falconbolt.core.config.HibernateHelper;

public class View implements ModelObserver{
    private static final Logger LOGGER = LogManager.getLogger(View.class);
    private Map<String , AbstractWorkView> loadedViews = new HashMap<String , AbstractWorkView>();
    private static View view;
    private Model model;
    private Control control;
    private MainFrame mainFrame = new MainFrame();
    private MainPanel mainPanel = new MainPanel(new BorderLayout());
    private StatusBarPanel statusBar;

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
    	this.loadWorkViews();
        this.initializeUI();
    }
    
    private void loadWorkViews() {
    	AbstractWorkView welcomeView = new WelcomeWorkView(new WelcomeViewController() , this.model);
    	this.mainPanel.loadWorkView(welcomeView);
    	this.loadedViews.put(welcomeView.getViewName(), welcomeView);
    	System.out.print(this.loadedViews.size());
    }

    public void setView (AbstractWorkView view){
    	model.setLiveView(view);
        this.mainPanel.displayWorkView(view.getViewName());
    }

    public void chooseWorkView(){
        this.setView(this.loadedViews.get(WelcomeWorkView.VIEW_NAME));
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
    
    @Override
    public void update(String msg) {
    	
    }

    private void initializeUI(){
        try{
            LOGGER.info("Initializing the UI");
            statusBar = new StatusBarPanel();
            mainFrame.setContentPane(new JPanel(new BorderLayout()));
            mainFrame.getContentPane().add(mainPanel , BorderLayout.CENTER);
            mainFrame.getContentPane().add(statusBar , BorderLayout.SOUTH);
            mainFrame.setTitle(Constant.APP_TITLE);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setSize(400 , 400);
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
