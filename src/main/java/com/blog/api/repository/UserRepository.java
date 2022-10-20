package com.blog.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.api.entity.Comment;
import com.blog.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Optional<User> findByUsername(String username);
	
	public Boolean existsByUsername(String username);
	
	public Boolean existsByEmail(String email);
	
	@Query(value = "SELECT * FROM user ORDER BY rand() LIMIT 1", nativeQuery = true)
	public User getRandom();
	
}