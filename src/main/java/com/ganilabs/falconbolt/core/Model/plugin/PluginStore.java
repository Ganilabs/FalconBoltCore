package com.ganilabs.falconbolt.core.Model.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ganilabs.falconbolt.interfaces.plugin.PluginAPI;

public class PluginStore {
	private Map<String , PluginAPI> loadedPlugins = new HashMap<>();
	
	public void addLoadedPlogin(PluginAPI plugin) {
		this.loadedPlugins.put(plugin.getPluginName(), plugin);
	}
	public Map<String , PluginAPI> getAllLoadedPlugins(){
		return this.loadedPlugins;
	}
	
	public void removePlugin(PluginAPI plugin) {
		this.loadedPlugins.remove(plugin);
	}
}
