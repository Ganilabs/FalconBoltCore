package com.ganilabs.falconbolt.core.Model;

import java.util.HashSet;
import java.util.Set;

public class Model {
    private final Set<ModelObserver> observers= new HashSet<>();

    private static Model model;
    private Model(){};

    public static Model getSingleton(){
        if(model == null){
            System.out.println("Creating new Model singleton in core");
            model = new Model();
            return model;
        }
        return model;
    }

    public void addModelObserver(ModelObserver observer){
        System.out.println("Registering new observer in Core Model");
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
