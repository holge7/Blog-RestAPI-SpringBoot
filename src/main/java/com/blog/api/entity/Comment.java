package com.blog.api.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	public String name;
	public String email;
	public String body;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postID", nullable = false)
	public Post post;

	public Comment() {}
	
	
	
}
