package com.ganilabs.falconbolt.core.View;

import javax.swing.*;

import com.ganilabs.falconbolt.core.constant.Constant;

import java.awt.*;

public class MainFrame extends JFrame{
    JPanel contentPane = new JPanel(new CardLayout());
    public void init(){
        setTitle(Constant.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Init Content pane
        
    }
}
