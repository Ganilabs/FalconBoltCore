package com.ganilabs.falconbolt.core.Control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;

import com.ganilabs.falconbolt.core.Model.tools.ToolsFactory;
import com.ganilabs.falconbolt.core.Model.tools.ToolsStore;
import com.ganilabs.falconbolt.interfaces.constants.PluginConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.plugin.PluginStore;
import com.ganilabs.falconbolt.core.config.SpringContextProvider;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.constant.InterProcessMessages;
import com.ganilabs.falconbolt.interfaces.plugin.MessageQueue;
import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;
import com.ganilabs.falconbolt.interfaces.plugin.PluginMessageDispatcher;

public class Control {
    private static Control control;
    private static final Logger LOGGER = LogManager.getLogger(Control.class);
    private Model model;
    private Map<String , MessageQueue> messageQueues = new HashMap<>();
    private Control(){};
    public static Control getSingleton() throws IllegalStateException{
        if(control == null){
            throw new IllegalStateException("Initialize controller first");
        }
        return control;
    }

    public static Control initSingleton(Model model){
        if(control != null){
            return control;
        }
        LOGGER.info("Creating new Controller in Core");
        control = new Control();
        control.model = model;
        return control;
    }

    public void init(){
        LOGGER.info("Initializing controller");
        this.loadPlugins();
		this.loadTools();
        this.setupMessageQueues();
        this.populateMessageQueuesAndPluginMessageDispatchers();
    }

	private void loadPlugins() {
    	Optional<PluginStore> pluginStoreOp = model.getPluginStore();
    	if(pluginStoreOp.isEmpty()) {
    		LOGGER.error("Plugins failed to load. Store is null");
    		model.externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
    	}
    	try {
    		PluginStore store= pluginStoreOp.get();
        	ServiceLoader<PluginAPI> serviceLoader = ServiceLoader.load(PluginAPI.class);
        	for(PluginAPI plugin : serviceLoader) {
				plugin.initPlugin();
        		store.addLoadedPlogin(plugin);
        		LOGGER.info("Loaded plugin : {} id :{} " , plugin.getPluginName() , plugin.getPluginId());
        	}
    	}catch(ServiceConfigurationError e) {
    		LOGGER.error(e.getMessage(), e);
    		model.externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
    	}
    	
    }

	private void loadTools() {
		Optional<PluginStore> pluginStoreOp = model.getPluginStore();
		if(pluginStoreOp.isEmpty()) {
			LOGGER.error("Plugins failed to load. Store is null");
			model.externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
		}
		PluginStore pluginStore = pluginStoreOp.get();
		Map<String , PluginAPI> pluginsAPIMap = pluginStore.getAllLoadedPlugins();
		Set<String> pluginKeySet = pluginsAPIMap.keySet();
		ToolsFactory toolsFactory = new ToolsFactory();
		for(String pluginKey : pluginKeySet){
			try{
				PluginAPI plugin = pluginsAPIMap.get(pluginKey);
				if(plugin.getPluginType().get(0).equals(PluginConstants.PluginTypeConstants.TOOL))
					toolsFactory.createToolAndAddToStore(plugin.getPluginType().get(1) , plugin);
			}catch(ClassNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
				model.externalNotifyLiveView(Constant.ErrorMessages.CUSTOM_ERROR_MESSAGE , "Failed to load tool ");
			}
		}
	}
    
    private void setupMessageQueues() {
    	Optional<InterProcessMessages> ipcMessagesOptional = this.getIPCMessages();
    	if(ipcMessagesOptional.isEmpty()) {
    		LOGGER.error("failed to load IPCMessages");
    	}
    	Set<String> messages = ipcMessagesOptional.get().getMessages();
    	for(String message : messages) {
    		MessageQueue msgQueue = new MessageQueueImpl(message);
    		this.messageQueues.put(msgQueue.getName(), msgQueue);
    		LOGGER.info("Loaded MessageQueue : {}" , message);
    	}
    }
    
    private void populateMessageQueuesAndPluginMessageDispatchers() {
		Optional<PluginStore> pluginStoreOp = this.model.getPluginStore();
		PluginStore store= pluginStoreOp.get();
		Set<Entry<String, PluginAPI>> loadedPluginSet = store.getAllLoadedPlugins().entrySet();
		Set<String> messagesListenedTo = new HashSet<>();
		Set<String> messagesDispatched = new HashSet<>();
		PluginMessageDispatcher dispatcher;
		for(Entry<String,PluginAPI> plugin : loadedPluginSet) {
			messagesListenedTo = plugin.getValue().getMessagesListenedTo();
			messagesDispatched = plugin.getValue().getMessagesDispatched();
			//configuring as listener
			for(String message : messagesListenedTo) {
				this.messageQueues.get(message).addListener(plugin.getValue().getPluginMessageListener());
			}
			
			//configuring as dispatcher
			dispatcher = plugin.getValue().getPluginMessageDispatcher();
			for(String dispatchedMessage : messagesDispatched) {
				dispatcher.addMessageQueue(this.messageQueues.get(dispatchedMessage));
			}
			LOGGER.info("Setup Listeners and dispatchers for {} plugin. id : {}" , plugin.getValue().getPluginName() , plugin.getValue().getPluginId());
		}
	}
    
    private Optional<InterProcessMessages> getIPCMessages(){
    	try {
    		return Optional.ofNullable(SpringContextProvider.getControllerContext().getBean(InterProcessMessages.class));
    	}catch(NoSuchBeanDefinitionException e) {
    		LOGGER.error(e.getMessage() , e);
    		this.model.externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
    	}catch(BeansException e) {
    		LOGGER.error(e.getMessage() , e);
    		this.model.externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
    	}
    	return Optional.empty();
    	
    }
}
