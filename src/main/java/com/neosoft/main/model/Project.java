package com.neosoft.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int projId;
	private String projName;
	private String duration;
	
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String name) {
		this.projName = name;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String dur) {
		this.duration = dur;
	}
	
	
	
}
