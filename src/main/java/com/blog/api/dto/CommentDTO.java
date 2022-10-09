package com.blog.api.dto;

import javax.validation.constraints.NotEmpty;

public class CommentDTO {

	public long id;
	public String userEmail;
	public String userName;
	
	@NotEmpty(message = "Body can't be null")
	public String body;
	public long postID;
	
	public CommentDTO() {}

	public CommentDTO(Long id, String userName, String userEmail, String body, long postID) {
		super();
		this.id = id;
		this.userName = userName;
		this.userEmail = userEmail;
		this.body = body;
		this.postID = postID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
