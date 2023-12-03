package com.ganilabs.falconbolt.core.Model.repository.project;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer project_id;
	@Column(name = "name" , length = 255 , nullable = true , unique = true)
	String name;
	@Column(name = "createdAt" , updatable = false)
	Timestamp createdAt = new Timestamp(new Date().getTime());
	
	@Column(name = "openedAt")
	Timestamp openedAt = new Timestamp(new Date().getTime());

	public Integer getProject_id() {
		return project_id;
	}

	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getOpenedAt() {
		return openedAt;
	}

	public void setOpenedAt(Timestamp openedAt) {
		this.openedAt = openedAt;
	}
	
	
}
