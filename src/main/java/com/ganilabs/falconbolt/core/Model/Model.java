package com.ganilabs.falconbolt.core.Model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.ganilabs.falconbolt.core.Model.tools.OpenedTools;
import com.ganilabs.falconbolt.core.Model.tools.ToolsStore;
import com.ganilabs.falconbolt.interfaces.pluginmessages.ScanResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeansException;

import com.ganilabs.falconbolt.core.Model.plugin.PluginStore;
import com.ganilabs.falconbolt.core.Model.repository.general.GeneralRepository;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectRepository;
import com.ganilabs.falconbolt.core.config.HibernateHelper;
import com.ganilabs.falconbolt.core.config.SpringContextProvider;
import com.ganilabs.falconbolt.core.constant.Constant;

public class Model {
    private static final Logger LOGGER = LogManager.getLogger(Model.class);
    private final Set<ModelObserver> observers= new HashSet<>();
    private final Set<ScanResultMessage> scanResultMessages= new HashSet<>();
    private static Model model;
    ModelObserver liveView;
    private Model(){};

    public static Model getSingleton(){
        if(model == null){
            LOGGER.info("Creating new Model singleton in core");
            model = new Model();
            return model;
        }
       
        return model;
    }
    
    public void init() {
    	try {
    		 HibernateHelper.initSessionFactory();
    	}catch(HibernateException e) {
    		LOGGER.error(e.getMessage() , e);
    		this.shutDownGracefully();
    		this.internalNotifyLiveView(Constant.ErrorMessages.ERROR_ENCOUNTERED);
    	}
    }
    
    public void shutDownGracefully() {
    	HibernateHelper.closeSessionFactory();
    }
    
    public void setLiveView(ModelObserver liveView) {
    	this.liveView = liveView;
    }
    public void addModelObserver(ModelObserver observer){
        LOGGER.info("Registering new observer in Core Model");
        this.observers.add(observer);
    }

    public void removeModelObserver(ModelObserver observer){
        this.observers.remove(observer);
    }

    //changeMsg is used to inform the observers which part of model is updated so
    //Only the interested views can take further action.
    public void notifyObservers(String changeMsg){
        for(ModelObserver observer : this.observers){
            observer.update(changeMsg);
        }
    }
    
    //to be used only from within model class hence private.
    private void internalNotifyLiveView(String msg) {
    	this.liveView.update(msg);
    }
    private void internalNotifyLiveView(String msg , String data) {
    	this.liveView.update(msg, data);
    }
    
    // to be used by external users like controller
    public void externalNotifyLiveView(String msg) {
    	this.liveView.update(msg);
    }
    
    public void externalNotifyLiveView(String msg ,String data) {
    	this.liveView.update(msg ,data);
    }
    
    public Optional<ProjectRepository> getProjectRepository(){
    	try {
    		return Optional.of((ProjectRepository) SpringContextProvider.getRepositoryContext().getBean(ProjectRepository.class));
    	} catch(BeansException e) {
    		LOGGER.error(e.getMessage() , e);
    		this.liveView.update(Constant.ErrorMessages.ERROR_ENCOUNTERED);
    	}
    	return Optional.empty();
    }
    
    public Optional<GeneralRepository> getGeneralRepository(){
    	try {
    		return Optional.of((GeneralRepository) SpringContextProvider.getRepositoryContext().getBean(GeneralRepository.class));
    	} catch(BeansException e) {
    		LOGGER.error(e.getMessage() , e);
    		this.liveView.update(Constant.ErrorMessages.ERROR_ENCOUNTERED);
    	}
    	return Optional.empty();
    }
    
    
    public Optional<PluginStore> getPluginStore(){
    	try {
    		return Optional.of((PluginStore)SpringContextProvider.getModelContext().getBean(PluginStore.class));
    	} catch(BeansException e) {
    		LOGGER.error(e.getMessage() , e);
    		this.liveView.update(Constant.ErrorMessages.ERROR_ENCOUNTERED);
    	}
    	return Optional.empty();	
    }

    public Optional<OpenedTools> getOpenedTools(){
        try {
            return Optional.of((OpenedTools) SpringContextProvider.getModelContext().getBean(OpenedTools.class));
        } catch(BeansException e) {
            LOGGER.error(e.getMessage() , e);
            this.liveView.update(Constant.ErrorMessages.ERROR_ENCOUNTERED);
        }
        return Optional.empty();
    }

    public Optional<ToolsStore> getToolsStore(){
        try{
            return Optional.of(SpringContextProvider.getModelContext().getBean(ToolsStore.class));
        }catch(BeansException e){
            LOGGER.error(e.getMessage() , e);
            this.liveView.update(Constant.ErrorMessages.ERROR_ENCOUNTERED);
        }
        return Optional.empty();
    }

    public Set<ScanResultMessage> getScanResultMessages() {
        return scanResultMessages;
    }

    public void addScanResultsMessage(ScanResultMessage result){
        this.scanResultMessages.add(result);
    }
}
