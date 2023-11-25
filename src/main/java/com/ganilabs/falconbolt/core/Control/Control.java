package com.ganilabs.falconbolt.core.Control;

import com.ganilabs.falconbolt.core.Model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Control {
    private static Control control;
    private static final Logger LOGGER = LogManager.getLogger(Control.class);
    private Model model;
    private Control(){};
    public static Control getSingleton() throws IllegalStateException{
        if(control == null){
            throw new IllegalStateException("Initialize controller first");
        }
        return control;
    }

    public static Control initSingleton(Model model){
        if(control != null){
            return control;
        }
        LOGGER.info("Creating new Controller in Core");
        control = new Control();
        control.model = model;
        return control;
    }

    public void init(){
        LOGGER.info("Initializing controller");
    }
}
