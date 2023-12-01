package com.ganilabs.falconbolt.core.View.workViews;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import com.ganilabs.falconbolt.core.Control.viewHandlers.WelcomeViewController;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;

public class WelcomeWorkView extends AbstractWorkView{
    public final static String VIEW_NAME = "WELCOME_VIEW";
    public final static Integer VIEW_ID = 1;
    private WelcomeViewController controller;
    private Model model;
    public WelcomeWorkView(WelcomeViewController controller , Model model){
    	super(model);
    	//controller is separated into sub controller for every work view.
    	this.controller = controller;
        JButton btn = new JButton("Click me");
        this.add(btn);
        this.setBackground(Color.BLACK);
        // Set a preferred size
        setPreferredSize(new Dimension(300, 200));
    }
    
    @Override
    public String getViewName() {
    	return VIEW_NAME;
    }

    @Override
    public void update(String msg) {
    	super.update(msg);
    }


}
