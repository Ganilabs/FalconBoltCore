package com.ganilabs.falconbolt.core.View.workViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.Control.Control;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;

public class WelcomeWorkView extends AbstractWorkView{
    private final static String VIEW_NAME = "WELCOME_VIEW";
    private final static Integer VIEW_ID = 1;

    public WelcomeWorkView(){
    	super(Model.getSingleton());
        JButton btn = new JButton("Click me");
        this.add(btn);
        this.setBackground(Color.BLACK);
        btn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		SwingUtilities.invokeLater(new Runnable() {
        			@Override
        			public void run() {
        				Control.getSingleton().handleClick();
        			}
        		});
        	}
        });
        // Set a preferred size
        setPreferredSize(new Dimension(300, 200));
    }
    
    
    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public Integer getViewId() {
        return VIEW_ID;
    }
    
    @Override
    public void update(String msg) {
    	switch(msg) {
    	case Constant.ModelChangeMessages.ERROR_ENCOUNTERED:
    		JOptionPane.showMessageDialog(new JFrame(), "Something went wrong", "Dialog",
    		        JOptionPane.ERROR_MESSAGE);
    	}
    }


}
