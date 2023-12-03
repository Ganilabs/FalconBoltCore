package com.ganilabs.falconbolt.core.Model.repository.project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ganilabs.falconbolt.core.config.HibernateHelper;
import com.ganilabs.falconbolt.core.exceptions.DataStateConflictException;

public class ProjectRepoImpl implements ProjectRepository {
	SessionFactory sf = HibernateHelper.getDefaultSession();
	@Override
	public void createNewProject(ProjectDTO projectData) throws DataStateConflictException{
		Project newProject = new Project();
		Session session = sf.openSession();
		if(projectWithNameExists(projectData.getProjectName() , session)) throw new DataStateConflictException("Project Name is already taken");
		newProject.setName(projectData.getProjectName());
		session.save(newProject);
		session.close();
	}

	@Override
	public ProjectDTO getProjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ProjectDTO> getAllProjects(){
		List<ProjectDTO> projectDTOs = new ArrayList<>();
		String hql = "SELECT P.id , P.name,P.createdAt FROM Project P";
		Session session = sf.openSession();
		List<Object[]> returnedProjects = session.createQuery(hql).list();
		for(Object[]  row: returnedProjects) {
			Project project = new Project();
			project.setName((String)row[1]);
			project.setProject_id((Integer)row[0]);
			project.setCreatedAt((Timestamp)row[2]);
			projectDTOs.add(ProjectDTO.fromEntity(project));
		}
		session.close();
		return projectDTOs;
	}
	
	private Boolean projectWithNameExists(String name , Session hibernateSession) {
		String hql = "SELECT P from Project P where P.name = :entityName";
		List<Project> resultList = hibernateSession.createQuery(hql)
		        .setParameter("entityName", name).list();
		if (resultList.isEmpty()) return false;
		return true;
	};

}
