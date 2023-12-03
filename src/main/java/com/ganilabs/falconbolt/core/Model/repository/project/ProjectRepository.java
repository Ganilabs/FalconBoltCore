package com.ganilabs.falconbolt.core.Model.repository.project;

import com.ganilabs.falconbolt.core.exceptions.DataStateConflictException;

public interface ProjectRepository {
	public void createNewProject(ProjectDTO projectData) throws DataStateConflictException;
	public Project getProjectByName(String name);
}
