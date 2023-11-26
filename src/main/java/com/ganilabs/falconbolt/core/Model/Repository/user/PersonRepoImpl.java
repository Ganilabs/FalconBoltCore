package com.ganilabs.falconbolt.core.Model.Repository.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ganilabs.falconbolt.core.config.HibernateHelper;

@Repository
public class PersonRepoImpl implements PersonRepo {
	SessionFactory sf = HibernateHelper.getDefaultSession();
	@Override
	public Person getPersonById(Integer id) {
		Session session = sf.openSession();
		Person foundPerson = session.get(Person.class, id);
		session.close();
		return foundPerson;
	}

}
