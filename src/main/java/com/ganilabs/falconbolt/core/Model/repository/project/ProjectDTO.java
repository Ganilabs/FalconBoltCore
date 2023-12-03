package com.ganilabs.falconbolt.core.Model.repository.project;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ProjectDTO{
	private String projectName;
	private Integer projectId;
	private LocalDateTime createdAt;
	private LocalDateTime openedAt;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getOpenedAt() {
		return openedAt;
	}
	public void setOpenedAt(LocalDateTime openedAt) {
		this.openedAt = openedAt;
	}
	
	public static ProjectDTO fromEntity(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectName(project.getName());
		projectDTO.setProjectId(project.getProject_id());
		projectDTO.setCreatedAt(project.getCreatedAt().toLocalDateTime());
		projectDTO.setOpenedAt(project.openedAt.toLocalDateTime());
		return projectDTO;
	}
	public static Comparator<ProjectDTO> COMPARE_BY_OPENEDTIME = new Comparator<ProjectDTO>() {
        public int compare(ProjectDTO one, ProjectDTO other) {
            return one.getOpenedAt().compareTo(other.getOpenedAt());
        }
    };
	
}
