package com.ganilabs.falconbolt.core.View;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.Control.Control;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;
import com.ganilabs.falconbolt.core.View.workViews.WelcomeWorkView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.awt.*;

public class View implements ModelObserver {
    private static final Logger LOGGER = LogManager.getLogger(View.class);
    private static View view;
    private Model model;
    private Control control;
    private MainFrame mainFrame;
    private MainPanel mainPanel;
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
        this.model.addModelObserver(this);
        this.initializeUI();
    }

    public void setView (AbstractWorkView view){
        this.mainPanel.displayWorkView(view.getViewName());
    }

    public void chooseWorkView(){
        this.setView(new WelcomeWorkView());
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
    public void update(String changeMessage) {
       switch (changeMessage){
           case Constant.ModelChangeMessages.NEW_PLUGIN_FOUND:
               System.out.println("new plugon discovered in model");
       }
    }

    private void initializeUI(){
        try{
            LOGGER.info("Initializing the UI");
            mainFrame = new MainFrame();
            mainPanel = new MainPanel(new BorderLayout());
            statusBar = new StatusBarPanel();
            mainPanel.loadWorkView(new WelcomeWorkView());
            mainFrame.setContentPane(new JPanel(new BorderLayout()));
            mainFrame.getContentPane().add(mainPanel , BorderLayout.CENTER);
            mainFrame.getContentPane().add(statusBar , BorderLayout.SOUTH);
            mainFrame.setTitle(Constant.APP_TITLE);
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setSize(400 , 400);
        } catch (Exception e){
            LOGGER.fatal(e.getMessage() , e);
            System.exit(1);
        }
    }
}
