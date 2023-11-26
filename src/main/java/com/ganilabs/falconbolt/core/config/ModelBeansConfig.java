package com.ganilabs.falconbolt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ganilabs.falconbolt.core.Model.plugin.PluginStore;

@Configuration
public class ModelBeansConfig {
	@Bean
	public PluginStore getPluginStore() {
		return new PluginStore();
	}
}
