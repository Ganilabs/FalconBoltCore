package com.ganilabs.falconbolt.core.config;

import com.ganilabs.falconbolt.core.Model.tools.OpenedTools;
import com.ganilabs.falconbolt.core.Model.tools.ToolsStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ganilabs.falconbolt.core.Model.plugin.PluginStore;

@Configuration
public class ModelBeansConfig {
	@Bean
	public PluginStore getPluginStore() {
		return new PluginStore();
	}
	@Bean
	public OpenedTools getOpenedTools(){
		return new OpenedTools();
	}
	@Bean
	public ToolsStore getToolsStore(){
		return new ToolsStore();
	}
}
