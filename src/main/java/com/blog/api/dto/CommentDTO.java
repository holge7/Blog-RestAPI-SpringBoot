package com.blog.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CommentDTO {

	public long id;
	
	@NotEmpty(message = "Name can't be null")
	public String name;
	
	@NotEmpty(message = "Email can't be null")
	@Email
	public String email;
	
	@NotEmpty(message = "Body can't be null")
	public String body;
	public long postID;
	
	public CommentDTO() {}

	public CommentDTO(Long id, String name, String email, String body, long postID) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
		this.postID = postID;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getPostID() {
		return postID;
	}

	public void setPostID(long postID) {
		this.postID = postID;
	}
	
	
	
}
