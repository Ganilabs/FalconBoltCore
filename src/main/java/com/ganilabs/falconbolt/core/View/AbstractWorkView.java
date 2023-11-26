package com.ganilabs.falconbolt.core.View;

import javax.swing.JPanel;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.ModelObserver;

public abstract class AbstractWorkView extends JPanel implements ModelObserver {
    public abstract String getViewName();
    public abstract Integer getViewId();
    Model model;
    protected AbstractWorkView(Model model) {
    	this.model = model;
    	model.addModelObserver(this);
    }

}
