package com.ganilabs.falconbolt.core.Model.tools;

import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

import javax.swing.*;
import java.util.List;

public abstract class AbstractTool {
    public abstract ImageIcon getToolIcon();
    public abstract String getToolName();
    public abstract List<String> getToolType();
    public abstract JPanel getToolUI();

}
