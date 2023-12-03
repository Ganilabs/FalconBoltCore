package com.ganilabs.falconbolt.core.config;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ganilabs.falconbolt.core.Model.repository.project.Project;

public class HibernateHelper {
	private static  SessionFactory sessionFactory;
	public static SessionFactory getDefaultSession() throws HibernateException{
		if(sessionFactory != null) return sessionFactory;
		Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class);
		sessionFactory = config.buildSessionFactory();
		return sessionFactory;
	}
	
	public static void initSessionFactory() throws HibernateException{
		Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class);
		sessionFactory = config.buildSessionFactory();
	}
	public static void closeSessionFactory() throws HibernateException {
		sessionFactory.close();
	}
}
