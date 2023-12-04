package com.ganilabs.falconbolt.core.Model.repository.project;

import java.util.List;

import com.ganilabs.falconbolt.core.exceptions.DataStateConflictException;

public interface ProjectRepository {
	public void createNewProject(ProjectDTO projectData) throws DataStateConflictException;
	public ProjectDTO getProjectByName(String name);
	public List<ProjectDTO> getAllProjects(); 
	public void deleteProjectById(Integer Id);
}
