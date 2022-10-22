package com.blog.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.blog.api.dto.PostDTO;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.PostRepository;
import com.blog.api.repository.RolRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.security.JwtTokenProvider;
import com.blog.api.seeder.Seeder;
import com.blog.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostTest {
	
	@Autowired
	Utils utils;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	Seeder seeder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
    @PersistenceContext
    private EntityManager entityManager;
    
	@BeforeEach
	public void init() {
		//Load data in h2
		seeder.loadRoles();
		seeder.loadUser();
		seeder.loadPost();
	}
	
	@Test
	@Transactional
	public void findByID() throws Exception {
		long id = postRepository.getLastPost().id;
		
		ResultActions resultActions = mockMvc.perform(get("/api/posts/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(id));
	}
	
	@Test
	@Transactional
	public void createPost() throws Exception {

		long id = postRepository.getLastPost().id+1;
		
		//Create post
		Post newPost = seeder.createPostRandom(id);
		
		//Parse post to json
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(newPost);
		
		String jwtString = utils.getAuth("jorge@gmail.com", "password");
		
		//Send request
		ResultActions result = mockMvc.perform(post("/api/posts")
				.header("Authorization", "Bearer " + jwtString)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON));
		
		//Check that request response is created
		result.andExpect(status().isCreated());
		
		// Assert that we have one row more
		assertEquals(id, postRepository.getLastPost().id);
		
	}
	
	@Test
	@Transactional
	public void editPost() throws Exception {

		// Create post
		long id = postRepository.getLastPost().id+1;
		String jwtString = utils.getAuth("jorge@gmail.com", "password");

		createPost(id, jwtString);

		
		// Edit post
		String newTitle = "Title updateado";
		String newDescription = "updateeeeeeee :)";
		String postContent = "yes";
		
		PostDTO editPost = new PostDTO(id, newTitle, newDescription, postContent);
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(editPost);
		
		// Send edit post
		ResultActions result = mockMvc.perform(put("/api/posts/"+id)
				.header("Authorization", "Bearer " + jwtString)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON));
		
		// Check if it was edited
		result.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(id))
		.andExpect(jsonPath("$.data.title").value(newTitle))
		.andExpect(jsonPath("$.data.description").value(newDescription))
		.andExpect(jsonPath("$.data.content").value(postContent));
	}
	
	@Test
	@Transactional
	public void edit_other_Post() throws Exception {
		String jwtString = utils.getAuth("jorge@gmail.com", "password");

		//Create one post with other person
		long id = postRepository.getLastPost().id+1;
		User user = userRepository.getLastUser();

		String jwtString2 = utils.getAuth(user.getEmail(), "password");
		createPost(id, jwtString2);
		
		// Edit post
		String newTitle = "Title updateado";
		String newDescription = "updateeeeeeee :)";
		String postContent = "yes";
		
		PostDTO editPost = new PostDTO(id, newTitle, newDescription, postContent);
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(editPost);
		
		// Send edit post
		ResultActions result = mockMvc.perform(put("/api/posts/"+id)
				.header("Authorization", "Bearer " + jwtString)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON));
		
		// Check if it was edited
		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	@Transactional
	public void delete_other_Post_with_admin() throws Exception {
		String jwtString = utils.getAuth("jorge@gmail.com", "password");

		//Create one post with other person
		long id = postRepository.getLastPost().id+1;
		User user = userRepository.getLastUser();

		String jwtString2 = utils.getAuth(user.getEmail(), "password");
		createPost(id, jwtString2);
		
		// Delete other post
		ResultActions result = mockMvc.perform(delete("/api/posts/"+id)
				.header("Authorization", "Bearer " + jwtString)
				.contentType(MediaType.APPLICATION_JSON));
		
		// Check if it was edited
		result.andExpect(status().isAccepted());
	}
	
	@Test
	@Transactional
	public void delete_other_Post() throws Exception {
		long id = postRepository.getLastPost().id+1;
		long lastUserID = userRepository.getLastUser().getId();
		
		User userCreator = userRepository.findById(lastUserID)
				.orElseThrow(()->new NotFoundException("User", lastUserID));
		
		User userDeleter = userRepository.findById(lastUserID-1)
				.orElseThrow(()->new NotFoundException("User", lastUserID-1));
		
		String jwtDelete = utils.getAuth(userDeleter.getEmail(), "password");

		//Create one post with other person

		String jwtCreate = utils.getAuth(userCreator.getEmail(), "password");
		createPost(id, jwtCreate);
		
		// Delete other post
		ResultActions result = mockMvc.perform(delete("/api/posts/"+id)
				.header("Authorization", "Bearer " + jwtDelete)
				.contentType(MediaType.APPLICATION_JSON));
		
		// Check if it was edited
		result.andExpect(status().isUnauthorized());
	}
	
	
	@Test
	@Transactional
	public void deletePost() throws Exception {
		long id = postRepository.getLastPost().id+1;
		String jwtString = utils.getAuth("jorge@gmail.com", "password");
		
		createPost(id, jwtString);

		
		ResultActions result = mockMvc.perform(delete("/api/posts/"+id)
				.header("Authorization", "Bearer " + jwtString)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isAccepted());
		
	}
	
	@Test
	@Transactional
	public void useWithBadAuth() throws Exception {
		long id = postRepository.getLastPost().id+1;
		String jwtString = utils.getAuth("jorge@gmail.com", "password");

		ResultActions result = createPost(id, jwtString+"Nop");

		result.andExpect(status().isUnauthorized());
	}
	
	
	
	public ResultActions createPost(long id, String jwt) throws Exception {
		Post newPost = seeder.createPostRandom(id);
		
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(newPost);
		
		ResultActions result = mockMvc.perform(post("/api/posts")
				.header("Authorization", "Bearer " + jwt)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON));
		
		return result;
	}
	

	
}
