package com.ganilabs.falconbolt.core.Model.tools;

import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

import java.util.HashSet;
import java.util.Set;

public class OpenedTools {
    private Set<AbstractTool> openedTools = new HashSet<>();
    public void addNewOpenedTool(AbstractTool tool){
        this.openedTools.add(tool);
    }
    public void closeTool(AbstractTool tool){
        this.openedTools.remove(tool);
    }
    public Set<AbstractTool> getAllOpenedTools(){
        return this.openedTools;
    }
}
