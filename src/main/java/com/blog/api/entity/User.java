package com.blog.api.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username", "email"})
	})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String username;
	private String email;
	private String password;
	private String avatar;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "userID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rolID", referencedColumnName = "id"))
	private Set<Rol> rol = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY ,mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
	public User() {}
	
	public User(String name, String username, String email, String password, Set<Rol> rol, String avatar) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.avatar = avatar;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Set<Rol> getRol() {
		return rol;
	}

	public void setRol(Set<Rol> rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", avatar=" + avatar + ", rol=" + rol + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getUsername()=" + getUsername() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword()
				+ ", getAvatar()=" + getAvatar() + ", getRol()=" + getRol() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


	
	
}
