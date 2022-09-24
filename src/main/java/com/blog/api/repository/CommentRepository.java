package com.blog.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.api.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	@Query(value = "SELECT * FROM comment c WHERE c.postid = :postID", nativeQuery = true)
	public List<Comment> findByPostID(long postID);
	
	public List<Comment> findByEmail(String email);
	
}
