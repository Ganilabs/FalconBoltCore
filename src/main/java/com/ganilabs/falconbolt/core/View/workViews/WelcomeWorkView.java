package com.ganilabs.falconbolt.core.View.workViews;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;

import javax.swing.*;
import java.awt.*;

public class WelcomeWorkView extends AbstractWorkView {
    private final static String VIEW_NAME = "WELCOME_VIEW";
    private final static Integer VIEW_ID = 1;

    public WelcomeWorkView(){
        JButton btn = new JButton("Click me");
        this.add(btn);
        this.setBackground(Color.BLACK);
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


}
