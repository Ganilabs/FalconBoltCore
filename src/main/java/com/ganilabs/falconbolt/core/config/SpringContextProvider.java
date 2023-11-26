package com.ganilabs.falconbolt.core.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextProvider {
	private static ApplicationContext repoContext = new AnnotationConfigApplicationContext(DatabaseRepositoryBeansConfig.class);
	//to be used in case of other beans issued
	public static ApplicationContext getDefaultContext() {
		return null;
	}
	
	public static ApplicationContext getRepositoryContext() {
		
		return repoContext;
	}
	
}
