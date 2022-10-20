package com.blog.api.dto;

public class UserDTO {

	private String urlImgBase = "http://localhost/img/avatars/";
	
	private String name;
	private String email;
	private String avatar;
	private String jwt;
	
	public UserDTO() {}
	
	public UserDTO(String name, String email, String avatar, String jwt) {
		this.name = name;
		this.email = email;
		this.avatar = urlImgBase+avatar;
		this.jwt = jwt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = urlImgBase+avatar;
	}
	
	public String getJwt() {
		return this.jwt;
	}
	
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + ", avatar=" + avatar + "]";
	}
	
}
