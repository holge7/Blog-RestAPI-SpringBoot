package com.blog.api;

import static org.mockito.ArgumentMatchers.longThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.blog.api.entity.Comment;
import com.blog.api.repository.CommentRepository;
import com.blog.api.repository.PostRepository;
import com.blog.api.seeder.Seeder;
import com.blog.api.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentTest {
	
	@Autowired
	Utils utils;
	
	@Autowired
	Seeder seeder;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init() {
		//Load data in h2
		seeder.loadRoles();
		seeder.loadUser();
		seeder.loadPost();
		seeder.loadComments();
	}
	
	@Test
	@Transactional
	public void create_comment() throws Exception {
		long id = commentRepository.getLastComment().id+1;
		
		//Create comment
		Comment comment = seeder.createCommentRandom(id);
		
		//Parse comment to json
		ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(comment);
		String jwtString = utils.getAuth("jorge@gmail.com", "password");
		
		long postID = postRepository.getLastPost().id;

		//Send request
		ResultActions resultActions = mockMvc.perform(post("/api/comments/"+postID)
				.header("Authorization", "Bearer " + jwtString)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(jsonPath("$.data.id").value(id))
		.andExpect(jsonPath("$.data.body").value(comment.body));
	}
	
	
	
	
	
}