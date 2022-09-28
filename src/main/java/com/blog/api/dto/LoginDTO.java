package com.blog.api.dto;

public class LoginDTO {
	
	public String usernameOrEmail;
	public String password;
	
	public LoginDTO() {}
	
	public LoginDTO(String usernameOrEmail, String password) {
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passwordString) {
		this.password = passwordString;
	}

	@Override
	public String toString() {
		return "LoginDTO [usernameOrEmail=" + usernameOrEmail + ", password=" + password + "]";
	}
	
	
	
}
