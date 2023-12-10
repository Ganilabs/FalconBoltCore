package com.ganilabs.falconbolt.core.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;

import com.ganilabs.falconbolt.core.Model.repository.general.General;
import com.ganilabs.falconbolt.core.Model.repository.project.Project;

public class HibernateHelper {
	public final static Logger LOGGER = LogManager.getLogger(HibernateHelper.class); 
	private static  SessionFactory sessionFactory;
	public static SessionFactory getDefaultSession() throws HibernateException{
		if(sessionFactory != null) return sessionFactory;
		Configuration config = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Project.class)
				.addAnnotatedClass(General.class);
		sessionFactory = config.buildSessionFactory();
		return sessionFactory;
	}
	
	public static void initSessionFactory() throws HibernateException{
		Configuration config = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Project.class)
				.addAnnotatedClass(General.class);
		sessionFactory = config.buildSessionFactory();
		HibernateHelper.runInitializeDML();
	}
	public static void closeSessionFactory() throws HibernateException {
		sessionFactory.close();
	}
	private static void runInitializeDML() {
		String initializeGeneralTable = "INSERT INTO General (opened_project_id) VALUES (NULL)";
		Session session = sessionFactory.openSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) {
				 try (Statement statement = connection.createStatement()) {
	                    ResultSet result = statement.executeQuery("SELECT * FROM General");
	                    if(!result.next()) {
	                    	statement.execute(initializeGeneralTable);
	                    }
	                    result.close();
	                    statement.close();
	                } catch (SQLException e) {
	                    LOGGER.error(e.getMessage() , e);
	                    closeSessionFactory();
	                }
			}
		});
		session.close();
	}
}
