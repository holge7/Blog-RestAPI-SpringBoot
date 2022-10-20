package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.api.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(value = "SELECT * FROM post ORDER BY id DESC LIMIT 1", nativeQuery = true)
	public Post getLastPost();
	
}
