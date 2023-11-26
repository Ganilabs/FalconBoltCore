package com.ganilabs.falconbolt.core.Model.Repository.user;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo {
	public Person getPersonById(Integer id);

}
