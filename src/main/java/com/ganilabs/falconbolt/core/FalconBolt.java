package com.ganilabs.falconbolt.core;

import com.ganilabs.falconbolt.core.View.View;

public class FalconBolt {
    public static void main(String[] args) {
        View view = View.getSingleton();
        view.init();
        view.chooseWorkView();
        view.startApp();
    }
}
