package com.ganilabs.falconbolt.core.View;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;
import com.ganilabs.falconbolt.core.constant.Constant;

public abstract class AbstractWorkView extends JPanel implements ModelObserver {
	public abstract String getViewName();
	public abstract void captureEventFromChildSubFrame(ViewMessage message);
    protected Model model;
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
    		break;
    	case Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD:
    		JOptionPane.showMessageDialog(new JFrame(), "Plugins failed to load", "Dialog",
    		        JOptionPane.ERROR_MESSAGE);
    		break;
    	case Constant.ErrorMessages.RESOURCE_FAILED_TO_LOAD:
    		JOptionPane.showMessageDialog(new JFrame(), "Resource Failed To Load", "Dialog",
    		        JOptionPane.WARNING_MESSAGE);
    		break;
    	}
    }
    
    @Override
	public void update(String changeMsg , String displayData) {
    	switch(changeMsg) {
    	case Constant.ViewMessages.OPERATION_SUCCESS:
    		JOptionPane.showMessageDialog(new JFrame(), displayData, "Dialog",
    		        JOptionPane.INFORMATION_MESSAGE);
    	case Constant.ErrorMessages.CUSTOM_ERROR_MESSAGE:
    		JOptionPane.showMessageDialog(new JFrame(), displayData, "Dialog",
    		        JOptionPane.ERROR_MESSAGE);
    		break;
    	}
    }

}
