package com.blog.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTO {
	
	public long id;
	
	@NotEmpty
	@Size(min=2, message = "The title must have 2 characters")
	public String title;
	
	@NotEmpty
	@Size(min=10, message = "The title must have 10 characters")
	public String description;
	
	@NotEmpty
	public String content;

	//User data
	public UserDTO user;
	
	public PostDTO() {}

	public PostDTO(long id, String title, String description, String content) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", user=" + user + "]";
	}

	
	
	
	
}
