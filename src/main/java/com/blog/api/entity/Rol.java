package com.blog.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long rolID;
	
	@Column(length = 60)
	private String name;
	
	public Rol() {}

	public long getRolID() {
		return rolID;
	}

	public void setRolID(long rolID) {
		this.rolID = rolID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
