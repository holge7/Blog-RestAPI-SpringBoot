package com.blog.api.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User user;
	
	@Column(nullable = false)
	public String title;
	
	@Column(nullable = false)
	public String description;
	
	@Column(nullable = false)
	public String content;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY ,mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Comment> comment = new HashSet<>();
	
	public Post() {}
	
	public Post(long id, String title, String description, String content, User user) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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

	public String getPostContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
/*
	public Set<Comment> getComment() {
		return comment;
	}
	
	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}
	*/
	@Override
	public String toString() {
		return "Post [id=" + id + ", postTitle=" + title + ", postDescription=" + description
				+ ", postContent=" + content + "]";
	}

	
	
}
