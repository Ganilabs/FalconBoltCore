package com.ganilabs.falconbolt.core.Model.repository.general;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ganilabs.falconbolt.core.config.HibernateHelper;

public class GeneralRepoImpl implements GeneralRepository{
	SessionFactory sf = HibernateHelper.getDefaultSession();
	@Override
	public void setOpenedProject(Integer projectId) throws HibernateException {
		Session session = sf.openSession();
		Transaction t = session.getTransaction();
		t.begin();
		General data= session.get(General.class, 1);
		data.setOpenedProjectId(projectId);
		session.update(data);
		t.commit();
		session.close();
	}

	@Override
	public Integer getOpenedProjectId() throws HibernateException {
		Session session = sf.openSession();
		General data = session.get(General.class, 1);
		return data.getOpenedProjectId();
	}

}
