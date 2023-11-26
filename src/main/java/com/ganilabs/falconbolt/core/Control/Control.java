package com.ganilabs.falconbolt.core.Control;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ganilabs.falconbolt.core.Constant;
import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.Repository.user.Person;
import com.ganilabs.falconbolt.core.Model.Repository.user.PersonRepo;
import com.ganilabs.falconbolt.core.Model.plugin.PluginStore;

public class Control {
    private static Control control;
    private static final Logger LOGGER = LogManager.getLogger(Control.class);
    private Model model;
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
        
    }
    
    private void loadPlugins() {
    	Optional<PluginStore> pluginStoreOp = model.getPluginStore();
    	if(pluginStoreOp.isEmpty()) {
    		LOGGER.error("Plugins failed to load. Store is null");
    		model.externalNotifyLiveView(Constant.ErrorMessages.PLUGINS_FAILED_TO_LOAD);
    	}
    	PluginStore store= pluginStoreOp.get();
    	//TODO : Continue from service loader
    }
    
    public void handleClick() {
    	Optional<PersonRepo> repo = this.model.getPersonRepository();
    	if(repo.isPresent()) {
    		Person person = repo.get().getPersonById(1);
        	System.out.print(person.getName());
    	}
    	
    }
}
