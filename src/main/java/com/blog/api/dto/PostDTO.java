package com.blog.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTO {
	
	public long id;
	public String userEmail;
	public String userName;
	
	@NotEmpty
	@Size(min=2, message = "The title must have 2 characters")
	public String title;
	
	@NotEmpty
	@Size(min=10, message = "The title must have 10 characters")
	public String description;
	
	@NotEmpty
	public String content;
	//public Set<CommentDTO> comment;
	
	public PostDTO() {}

	public PostDTO(long id, String userEmail, String userName,String title, String description, String content) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.userName = userName;
		this.title = title;
		this.description = description;
		this.content = content;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", postTitle=" + title + ", postDescription=" + description
				+ ", postContent=" + content + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
/*
	public Set<CommentDTO> getComment() {
		return comment;
	}

	public void setComment(Set<CommentDTO> comment) {
		this.comment = comment;
	}
*/
	
	
	
	
}
