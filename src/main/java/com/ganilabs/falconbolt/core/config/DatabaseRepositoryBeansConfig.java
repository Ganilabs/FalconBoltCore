package com.ganilabs.falconbolt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ganilabs.falconbolt.core.Model.Repository.user.PersonRepo;
import com.ganilabs.falconbolt.core.Model.Repository.user.PersonRepoImpl;


@Configuration
public class DatabaseRepositoryBeansConfig {
	@Bean
	public PersonRepo getPersonRepo() {
		return new PersonRepoImpl();
	}
}
