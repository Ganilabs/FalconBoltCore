package com.ganilabs.falconbolt.core.View.components;

import com.ganilabs.falconbolt.core.Model.tools.AbstractTool;
import com.ganilabs.falconbolt.core.View.ChildFrameListener;
import com.ganilabs.falconbolt.core.View.ViewMessage;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.DisplayTextResources;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectedToolPopupMenu extends AbstractPopUpMenu{
    AbstractTool tool;
    public SelectedToolPopupMenu(AbstractTool tool , ChildFrameListener parent) {
        super(parent);
        this.tool = tool;
        this.initMenuItems();
    }

    private void initMenuItems(){
        JMenuItem close = new MenuItem(DisplayTextResources.CLOSE);
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.captureEventFromChildSubFrame(new ViewMessage(Constant.ToolMessages.CLOSE_SELECTED_TOOL , tool));
            }
        });
        this.add(close);
    }

}
