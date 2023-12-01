package com.ganilabs.falconbolt.core.View;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;

public abstract class AbstractWorkView extends JPanel implements ModelObserver {
	public abstract String getViewName();
    Model model;
    protected AbstractWorkView(Model model) {
    	this.model = model;
    	model.addModelObserver(this);
    }
    
    @Override
    public void update(String msg) {
    	switch(msg) {
    	case Constant.ErrorMessages.ERROR_ENCOUNTERED:
    		JOptionPane.showMessageDialog(new JFrame(), "Something went wrong", "Dialog",
    		        JOptionPane.ERROR_MESSAGE);
    	case Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD:
    		JOptionPane.showMessageDialog(new JFrame(), "Plugins failed to load", "Dialog",
    		        JOptionPane.ERROR_MESSAGE);
    	}
    }

}
