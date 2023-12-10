package com.ganilabs.falconbolt.core.Model.repository.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Contains general key value based data for whole application.
@Entity
public class General {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	Integer id;
	@Column(name = "opened_project_id")
	Integer openedProjectId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOpenedProjectId() {
		return openedProjectId;
	}
	public void setOpenedProjectId(Integer openedProjectId) {
		this.openedProjectId = openedProjectId;
	}
	
	
}
