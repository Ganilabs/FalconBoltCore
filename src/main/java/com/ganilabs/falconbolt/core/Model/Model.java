package com.ganilabs.falconbolt.core.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Model {
    private static final Logger LOGGER = LogManager.getLogger(Model.class);
    private final Set<ModelObserver> observers= new HashSet<>();

    private static Model model;
    private Model(){};

    public static Model getSingleton(){
        if(model == null){
            LOGGER.info("Creating new Model singleton in core");
            model = new Model();
            return model;
        }
        return model;
    }

    public void addModelObserver(ModelObserver observer){
        LOGGER.info("Registering new observer in Core Model");
        this.observers.add(observer);
    }

    public void removeModelObserver(ModelObserver observer){
        this.observers.remove(observer);
    }

    //changeMsg is used to inform the observers which part of model is updated so
    //Only the interested views can take further action.
    private void notifyObservers(String changeMsg){
        for(ModelObserver observer : this.observers){
            observer.update(changeMsg);
        }
    }
}
