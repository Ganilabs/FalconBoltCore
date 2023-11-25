package com.ganilabs.falconbolt.core;

import com.ganilabs.falconbolt.core.Control.Control;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.View.View;

public class FalconBolt {
    public static void main(String[] args) {
        View view = View.initSingleton(Model.getSingleton() , Control.initSingleton(Model.getSingleton()));
        Control control = Control.getSingleton();
        control.init();
        view.init();
        view.chooseWorkView();
        view.startApp();
    }
}
