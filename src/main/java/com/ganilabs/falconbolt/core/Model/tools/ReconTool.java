package com.ganilabs.falconbolt.core.Model.tools;

import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

import javax.swing.*;
import java.util.List;

public class ReconTool extends AbstractTool{
    private ImageIcon icon;
    private String name;
    private List<String> toolType;

    private JPanel toolUI;
    public ReconTool(PluginAPI plugin){
        this.toolType = plugin.getPluginType();
        this.toolUI = plugin.getPluginJPanel();
        this.icon = plugin.getPluginIcon();
        this.name = plugin.getPluginName();
    }
    @Override
    public ImageIcon getToolIcon() {
        return this.icon;
    }

    @Override
    public String getToolName() {
       return this.name;
    }

    @Override
    public List<String> getToolType() {
        return this.toolType;
    }

    @Override
    public JPanel getToolUI() {
        return this.toolUI;
    }
}
