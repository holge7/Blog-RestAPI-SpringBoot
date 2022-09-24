package com.blog.api.dto;

public class PostDTO {
	
	public long postID;
	public String postTitle;
	public String postDescription;
	public String postContent;
	
	public PostDTO() {}

	public PostDTO(long postID, String postTitle, String postDescription, String postContent) {
		super();
		this.postID = postID;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.postContent = postContent;
	}

	@Override
	public String toString() {
		return "PostDTO [postID=" + postID + ", postTitle=" + postTitle + ", postDescription=" + postDescription
				+ ", postContent=" + postContent + "]";
	}
	
	
	
}
