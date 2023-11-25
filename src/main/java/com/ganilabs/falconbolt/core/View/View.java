package com.ganilabs.falconbolt.core.View;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;
import com.ganilabs.falconbolt.core.View.workViews.WelcomeWorkView;

import javax.swing.*;
import java.awt.*;

public class View implements ModelObserver {
    private static View view;
    private Model model;
    private MainFrame mainFrame;
    private MainPanel mainPanel;
    private StatusBarPanel statusBar;

    private View(){
    }
    public static View getSingleton(Model model){
        if(view == null){
            view = new View();
            view.model = model;
        }
        return view;
    }

    public void init(){
        try{
            this.model.addModelObserver(this);
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
            e.printStackTrace();
            System.exit(1);
        }
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
            e.printStackTrace();
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
}
