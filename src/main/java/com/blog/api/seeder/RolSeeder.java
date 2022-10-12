package com.blog.api.seeder;

import java.util.Collections;
import java.util.Optional;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.entity.Comment;
import com.blog.api.entity.Post;
import com.blog.api.entity.Rol;
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.CommentRepository;
import com.blog.api.repository.PostRepository;
import com.blog.api.repository.RolRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.service.RolService;
import com.github.javafaker.Faker;

@RestController
@RequestMapping("api/seed")
public class RolSeeder{
	
	int userNumber = 5;
	int postsNumber = 20;
	int commentNumber = 40;
	
	
	RolRepository rolRepository;
	UserRepository userRepository;
	PostRepository postRepository;
	CommentRepository commentRepository;
	RolService rolService;
	Faker faker;
	
	
	public RolSeeder(RolRepository rolRepository, RolService rolService, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
		this.rolRepository = rolRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.rolService = rolService;
		this.faker = new Faker();
	}
	
	@GetMapping("/seed")
	private void seed() {
		loadRoles();
		loadUser();
		loadPost();
		loadComments();
	}

	@GetMapping("/roles")
	private void loadRoles() {
		System.out.println("loadRoles - Starting");
		//rolService.truncatee();
		Rol rolUser = new Rol(1, "ROLE_USER");
		Rol rolAdmin = new Rol(2, "ROLE_ADMIN");
		this.rolRepository.save(rolUser);
		this.rolRepository.save(rolAdmin);
	}
	
	@GetMapping("/user")
	private void loadUser() {
		System.out.println("loadUser - Starting");
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String password = passwordEncoder.encode("password");
		Rol rolUser = rolRepository.findByName("ROLE_USER").get();
		Rol rolAdmin = rolRepository.findByName("ROLE_ADMIN").get();
		User user = new User(1, "Jorge", "Holge", "jorge@gmail.com", password, Collections.singleton(rolAdmin));
		userRepository.save(user);
		
		for (int i = 2; i < userNumber; i++) {
			user = new User();
			user.setId(i);
			user.setName(faker.name().firstName());
			user.setUsername(faker.name().username());
			user.setEmail(faker.internet().emailAddress());
			user.setPassword(password);
			user.setRol(Collections.singleton(rolUser));
			userRepository.save(user);
		}
	}
	
	@GetMapping("/post")
	private void loadPost() {
		Post post;
		
		for (int i = 1; i < postsNumber; i++) {
			post = new Post();
			post.setId(i);
			post.setTitle(faker.lorem().sentence());
			post.setDescription("#"+faker.lorem().word());
			post.setContent(faker.lorem().paragraph());
			post.setUser(searchUser());
			postRepository.save(post);
		}
	}
	
	@GetMapping("/comment")
	private void loadComments() {
		Comment comment;
		
		for (int i = 1; i < commentNumber; i++) {
			comment = new Comment();
			comment.setId(i);
			comment.setBody(faker.lorem().sentence());
			comment.setUser(searchUser());
			comment.setPost(searchPost());
			commentRepository.save(comment);
		}
		
	}
	
	private User searchUser() {
		return userRepository.findById((long) faker.number().numberBetween(1, userNumber))
				.orElseThrow(() -> new NotFoundException("user in seeder"));
	}
	
	private Post searchPost() {
		return postRepository.findById((long) faker.number().numberBetween(1, postsNumber))
				.orElseThrow(() -> new NotFoundException("post in seeder"));
	}
	
	
}
