package com.ganilabs.falconbolt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ganilabs.falconbolt.core.Model.repository.project.ProjectRepoImpl;
import com.ganilabs.falconbolt.core.Model.repository.project.ProjectRepository;


@Configuration
public class DatabaseRepositoryBeansConfig {
	@Bean
	public ProjectRepository getProjectRepository() {
		return new ProjectRepoImpl();
	}
}
