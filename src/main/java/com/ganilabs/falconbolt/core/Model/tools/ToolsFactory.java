package com.ganilabs.falconbolt.core.Model.tools;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.interfaces.constants.ToolConstants;
import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ToolsFactory {
    public final static Logger LOGGER = LogManager.getLogger();
    private ToolsStore toolsStore;
    public ToolsFactory(){
        Optional<ToolsStore> toolsStoreOp = Model.getSingleton().getToolsStore();
        if(toolsStoreOp.isEmpty()) {
            LOGGER.error("Tools failed to load. Store is null");
            Model.getSingleton().externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
        }
        this.toolsStore = toolsStoreOp.get();
    }

    public ReconTool createReconToolFromPlugin(PluginAPI pluginAPI){
        return new ReconTool(pluginAPI);
    }

    public  AbstractTool createToolByType(String type , PluginAPI plugin) throws ClassNotFoundException{
        switch (type){
            case ToolConstants.ToolTypeConstants.RECON:
                return new ReconTool(plugin);
        }
        throw new ClassNotFoundException("Tool type of type " + type + " not found");
    }

    public void createToolAndAddToStore(String type , PluginAPI plugin) throws ClassNotFoundException{
        AbstractTool tool = this.createToolByType(type , plugin);
        this.toolsStore.addLoadedTool(tool);
    }
}
