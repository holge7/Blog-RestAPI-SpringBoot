package com.blog.api.seeder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;

import org.apache.naming.java.javaURLContextFactory;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;
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
import com.blog.api.util.ApiResponse;
import com.github.javafaker.Faker;

import io.github.classgraph.Resource;


@RestController
@RequestMapping("api/seed")
public class Seeder{
	
	int userNumber = 5;
	int postsNumber = 20;
	int commentNumber = 40;
	
	
	
	
	RolRepository rolRepository;
	UserRepository userRepository;
	PostRepository postRepository;
	CommentRepository commentRepository;
	RolService rolService;
	Faker faker;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	public Seeder(RolRepository rolRepository, RolService rolService, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
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
	public void loadRoles() {
		System.out.println("loadRoles - Starting");
		Rol rolUser = new Rol(1, "ROLE_USER");
		Rol rolAdmin = new Rol(2, "ROLE_ADMIN");
		this.rolRepository.save(rolUser);
		this.rolRepository.save(rolAdmin);
	}
	
	@GetMapping("/user")
	public void loadUser() {
		System.out.println("loadUser - Starting");
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String password = passwordEncoder.encode("password");
		Rol rolUser = rolRepository.findByName("ROLE_USER").get();
		Rol rolAdmin = rolRepository.findByName("ROLE_ADMIN").get();
		User user = new User("Jorge", "Holge", "jorge@gmail.com", password, Collections.singleton(rolAdmin), "avatar1.jpg");
		userRepository.save(user);
		
		for (int i = 2; i < userNumber; i++) {
			user = createUserRandom(i, "password");
			userRepository.save(user);
		}
	}
	
	@GetMapping("test")
	private ResponseEntity<ApiResponse> test() {
		org.springframework.core.io.Resource img = resourceLoader.getResource("avatars/avatar1.jpg");
		
		ApiResponse response = new ApiResponse(img);
		
		return new ResponseEntity<ApiResponse>(
				response,
				HttpStatus.OK
				);
		
	}
	
	
	@GetMapping("/post")
	public void loadPost() {
		Post post;
		
		for (int i = 1; i < postsNumber; i++) {
			post = createPostRandom(i);
			postRepository.save(post);
		}
	}
	
	@GetMapping("/comment")
	public void loadComments() {
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
		return userRepository.getRandom();
	}
	
	private Post searchPost() {
		return postRepository.findById((long) faker.number().numberBetween(1, postsNumber))
				.orElseThrow(() -> new NotFoundException("post in seeder"));
	}
	
	public User createUserRandom(long id, String password) {
		Rol rolUser = rolRepository.findByName("ROLE_USER").get();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pass = passwordEncoder.encode(password);
		User user = new User();
		user.setId(id);
		user.setName(faker.name().firstName());
		user.setUsername(faker.name().username());
		user.setEmail(faker.internet().emailAddress());
		user.setPassword(pass);
		user.setAvatar("avatar"+id+".jpg");
		user.setRol(Collections.singleton(rolUser));
		
		return user;
	}
	
	public Post createPostRandom(long id) {
		Post post = new Post();
		
		post.setTitle(faker.lorem().sentence());
		post.setDescription("#aaaaaaaaaaa"+faker.lorem().word());
		post.setContent(faker.lorem().paragraph(2));
		post.setUser(searchUser());
		
		return post;
	}
	
	public Comment createCommentRandom(long id) {
		Comment comment = new Comment();
		
		comment.setId(id);
		comment.setBody(faker.lorem().sentence());
		comment.setUser(searchUser());
		comment.setPost(searchPost());
		commentRepository.save(comment);
		
		return comment;
	}
	
}
