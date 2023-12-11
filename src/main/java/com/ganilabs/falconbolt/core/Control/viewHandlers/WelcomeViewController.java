package com.ganilabs.falconbolt.core.Control.viewHandlers;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ganilabs.falconbolt.core.Model.tools.OpenedTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.repository.general.GeneralRepository;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectDTO;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectRepository;
import com.ganilabs.falconbolt.core.View.AbstractWorkView;
import com.ganilabs.falconbolt.core.View.View;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.exceptions.DataStateConflictException;

public class WelcomeViewController {
	private Model model = Model.getSingleton();
	private ProjectRepository projectRepo;
	private GeneralRepository generalRepo;
	private final static Logger LOGGER = LogManager.getLogger(WelcomeViewController.class);
	
	public WelcomeViewController() {
		try {
			Optional<ProjectRepository> repoOp =  model.getProjectRepository();
			if(repoOp.isEmpty()) throw new NoSuchElementException("ProjectRepository not found");
			projectRepo = repoOp.get();
			Optional<GeneralRepository> generalRepoOp = model.getGeneralRepository();
			if(generalRepoOp.isEmpty()) throw new NoSuchElementException("ProjectRepository not found");
			generalRepo = generalRepoOp.get();
		}catch(NoSuchElementException e) {
			LOGGER.error(e.getMessage() , e);
			this.model.externalNotifyLiveView(Constant.ErrorMessages.ERROR_ENCOUNTERED);
		}
	}
	public void createNewProject(String projectName) {
		try {
			ProjectDTO project = new ProjectDTO();
			project.setProjectName(projectName);
			projectRepo.createNewProject(project);
			this.model.externalNotifyLiveView(Constant.ViewMessages.OPERATION_SUCCESS , "New Project Created");
			this.model.notifyObservers(Constant.ModelChangeMessages.PROJECT_CRUD);
		}catch(DataStateConflictException e) {
			LOGGER.error(e.getMessage() , e);
			this.model.externalNotifyLiveView(Constant.ErrorMessages.CUSTOM_ERROR_MESSAGE , e.getMessage());
		}
		
	}
	
	public List<ProjectDTO> getAllProjects() {
		return projectRepo.getAllProjects();
	}
	
	public List<ProjectDTO> getAllProjectsSortedByOpeningTime(Integer numRecords){
		try {
			List<ProjectDTO> receivedProjects= projectRepo.getAllProjects();
			Collections.sort(receivedProjects , ProjectDTO.COMPARE_BY_OPENEDTIME);
			Collections.reverse(receivedProjects);
			return receivedProjects.stream().limit(numRecords).collect(Collectors.toList());
		}catch(HibernateException e) {
			LOGGER.error(e.getMessage() , e);
			this.model.externalNotifyLiveView(Constant.ErrorMessages.ERROR_ENCOUNTERED);
		}
		return List.of();
	}
	
	public void deleteProjectByProjectId(Integer id) {
		try {
			projectRepo.deleteProjectById(id);
			model.notifyObservers(Constant.ModelChangeMessages.PROJECT_CRUD);
		}catch(HibernateException e) {
			LOGGER.error(e.getMessage() , e);
			this.model.externalNotifyLiveView(Constant.ErrorMessages.ERROR_ENCOUNTERED);
		}
		
	}
	
	public void openSelectedProject(String viewName , Integer projectId) {
		this.preOpenProject();
		generalRepo.setOpenedProject(projectId);
		View.getSingleton().setView(viewName);
	}

	private void preOpenProject(){
		Optional<OpenedTools> openedToolsOptional= model.getOpenedTools();
		if(openedToolsOptional.isEmpty()) return;
		OpenedTools openedTools = openedToolsOptional.get();
		openedTools.closeAllTools();
	}
}
