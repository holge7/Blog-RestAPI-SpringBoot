package com.blog.api.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTO {
	
	public long postID;
	
	@NotEmpty
	@Size(min=2, message = "The title must have 2 characters")
	public String postTitle;
	
	@NotEmpty
	@Size(min=10, message = "The title must have 10 characters")
	public String postDescription;
	
	@NotEmpty
	public String postContent;
	public Set<CommentDTO> comment;
	
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
				+ ", postContent=" + postContent + ", comment=" + comment + "]";
	}

	public long getPostID() {
		return postID;
	}

	public void setPostID(long postID) {
		this.postID = postID;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Set<CommentDTO> getComment() {
		return comment;
	}

	public void setComment(Set<CommentDTO> comment) {
		this.comment = comment;
	}

	
	
	
	
}
