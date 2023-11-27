package com.ganilabs.falconbolt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ganilabs.falconbolt.core.constant.InterProcessMessages;

@Configuration
public class ControllerBeansConfig {
	
	@Bean
	public InterProcessMessages getIPCMessages() {
		return new InterProcessMessages();
	}

}
