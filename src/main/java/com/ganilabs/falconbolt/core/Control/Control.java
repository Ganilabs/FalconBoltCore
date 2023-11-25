package com.ganilabs.falconbolt.core.Control;

import com.ganilabs.falconbolt.core.Model.Model;

public class Control {
    private static Control control;
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
        System.out.println("Creating new Controller in Core");
        control = new Control();
        control.model = model;
        return control;
    }

    public void init(){
        System.out.println("Initializing Core Controller");
    }
}
