package com.blog.api.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long postID;
	
	@Column(nullable = false)
	public String postTitle;
	
	@Column(nullable = false)
	public String postDescription;
	
	@Column(nullable = false)
	public String postContent;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Comment> comment = new HashSet<>();
	
	public Post() {}
	
	public Post(long postID, String postTitle, String postDescription, String postContent) {
		super();
		this.postID = postID;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.postContent = postContent;
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
	
}
