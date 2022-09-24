package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
}
