package com.ganilabs.falconbolt.core.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextProvider {
	private static ApplicationContext repoContext = new AnnotationConfigApplicationContext(DatabaseRepositoryBeansConfig.class);
	private static ApplicationContext modelContext = new AnnotationConfigApplicationContext(ModelBeansConfig.class);
	private static ApplicationContext controllerContext = new AnnotationConfigApplicationContext(ControllerBeansConfig.class);
	//to be used in case of other beans issued
	public static ApplicationContext getModelContext() {
		return modelContext;
	}
	
	public static ApplicationContext getRepositoryContext() {	
		return repoContext;
	}
	
	public static ApplicationContext getControllerContext() {
		return controllerContext;
	}
	
}
