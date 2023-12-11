package com.ganilabs.falconbolt.core.Model.tools;

import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

import java.util.HashMap;
import java.util.Map;

public class ToolsStore {
    private Map<String , AbstractTool> loadedTools = new HashMap<>();

    public void addLoadedTool(AbstractTool tool) {
        this.loadedTools.put(tool.getToolName(), tool);
    }
    public Map<String , AbstractTool> getAllLoadedTools(){
        return this.loadedTools;
    }

    public void removeTools(AbstractTool tool) {
        this.loadedTools.remove(tool.getToolName());
    }
}
