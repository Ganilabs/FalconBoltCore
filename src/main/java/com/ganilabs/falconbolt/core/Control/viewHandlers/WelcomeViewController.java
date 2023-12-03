package com.ganilabs.falconbolt.core.Control.viewHandlers;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ganilabs.falconbolt.core.Model.Model;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectDTO;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectRepository;
import com.ganilabs.falconbolt.core.constant.Constant;
import com.ganilabs.falconbolt.core.exceptions.DataStateConflictException;

public class WelcomeViewController {
	private Model model = Model.getSingleton();
	private final static Logger LOGGER = LogManager.getLogger(WelcomeViewController.class);
	
	public void createNewProject(String projectName) {
		try {
			Optional<ProjectRepository> repoOp =  model.getProjectRepository();
			if(repoOp.isEmpty()) throw new NoSuchElementException("ProjectRepository not found");
			ProjectRepository repo = repoOp.get();
			ProjectDTO project = new ProjectDTO();
			project.setProjectName(projectName);
			repo.createNewProject(project);
			this.model.externalNotifyLiveView(Constant.ViewMessages.OPERATION_SUCCESS , "New Project Created");
		}catch(NoSuchElementException e) {
			LOGGER.error(e.getMessage() , e);
			this.model.externalNotifyLiveView(Constant.ErrorMessages.ERROR_ENCOUNTERED);
		}catch(DataStateConflictException e) {
			LOGGER.error(e.getMessage() , e);
			this.model.externalNotifyLiveView(Constant.ErrorMessages.CUSTOM_ERROR_MESSAGE , e.getMessage());
		}
		
	}
}
