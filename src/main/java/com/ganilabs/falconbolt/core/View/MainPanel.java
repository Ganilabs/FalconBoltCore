package com.ganilabs.falconbolt.core.View;

import javax.swing.*;
import java.awt.*;
import java.util.logging.LogManager;

public class MainPanel extends JPanel{
    private JPanel workArea = new JPanel();
    private CardLayout workLayout = new CardLayout();

    MainPanel(LayoutManager manager){
        super(manager);
        workArea.setLayout(workLayout);
        this.add(workArea);
    }

    public void displayWorkView(String viewName) {
        this.workLayout.show(this.workArea , viewName);
    }

    public void loadWorkView(AbstractWorkView view) throws NullPointerException{
        this.workArea.add(view , view.getViewName());
    }
}
