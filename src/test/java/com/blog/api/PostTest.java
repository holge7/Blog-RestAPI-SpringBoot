package com.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.blog.api.assembler.PostAssembler;
import com.blog.api.controller.PostController;
import com.blog.api.dto.PostDTO;
import com.blog.api.exception.NotFoundException;
import com.blog.api.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(PostController.class) 
@ComponentScan({ "com.blog.api" })
public class PostTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PostService postService;
	
	@Autowired
	PostAssembler assembler;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void config() {
		objectMapper = new ObjectMapper();
	}
	/*
	private PostDTO post1 = new PostDTO(1L, "Test 1", "description 1", "content 1");
	private PostDTO post2 = new PostDTO(2L, "Test 2", "description 2", "content 2");
	private PostDTO post3 = new PostDTO(3L, "Test 3", "description 3", "content 3");
	private PostDTO post4 = new PostDTO(4L, "Test 4", "description 4", "content 4");

	private static String POST_PATH = "/post";
	private String resourceName = "Post";
	
	/**
	 * Find one post for ID
	 * @throws Exception
	 */
	/*@Test
	void findByID() throws Exception {
		when(postService.findByID(1)).thenReturn(post1);
		
		ResultActions result = mockMvc.perform(get("/post/1").contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		validPost(result, post1);
		
		verify(postService).findByID(1);
	}
	
	@Test
	void findByIDWrong() throws Exception {
		Long id = 1L;
		when(postService.findByID(id)).thenThrow(new NotFoundException(resourceName, id));
		
		ResultActions result = mockMvc.perform(get("/post/"+id).contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.msg").value("Could not found ["+resourceName+"]:["+id+"]"));
		
		verify(postService).findByID(id);
	}
	
	@Test
	void createPost() throws Exception {
		
		PostDTO newPost = new PostDTO(3, "new Post", "description new", "content new");

		when(postService.create(any())).then(invocation -> {
			PostDTO savePost = invocation.getArgument(0);
			savePost.postID = 3;
			return savePost;
		});
		
		ResultActions resultAction = mockMvc.perform(post("/post/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newPost)));
		 
		 resultAction
		 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		 .andExpect(status().isCreated());
		 
		 validPost(resultAction, newPost);
	}
	
	@Test
	void getAllPosts() throws Exception{
		List<PostDTO> list = Arrays.asList(post1, post2, post3, post4);

		when(postService.getAllPosts()).thenReturn(list);
		
		ResultActions resultAction = mockMvc //
				.perform(get("/post/all") //
				.contentType(MediaType.APPLICATION_JSON));
		
		resultAction.andExpect(status().isOk()) //
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
		for (int i = 0; i < list.size(); i++) {
			validPostList(i, resultAction, list.get(i));			
		}
	}
	
	@Test
	void deletePost() throws Exception{
		when(postService.deletePostById(1)).thenReturn(post1);
		
		ResultActions resultActions = mockMvc //
				.perform(delete(POST_PATH+"/1")
				.contentType(MediaType.APPLICATION_JSON));
		
		resultActions.andExpect(status().isOk());
		validPost(resultActions, post1);
	}
	
	@Test
	void editPost() throws Exception{
		PostDTO postEdited = new PostDTO(1, "Edited title", "Edited description", "Edited content");

		when(postService.editPost(any())).thenReturn(postEdited);
		

		ResultActions resultActions = mockMvc.perform(put(POST_PATH+"/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postEdited)));
		
		resultActions.andExpect(status().isOk());
		validPost(resultActions, postEdited);
		
	}
	*/
	/*private void validPostList(int index, ResultActions result, PostDTO post) throws Exception {
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data["+index+"].id").value(post.id))
		.andExpect(jsonPath("$.data["+index+"].postTitle").value(post.title))
		.andExpect(jsonPath("$.data["+index+"].postDescription").value(post.description))
		.andExpect(jsonPath("$.data["+index+"].postContent").value(post.content));
	}
	
	private void validPost(ResultActions result, PostDTO post) throws Exception {
		result.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.id").value(post.id))
		.andExpect(jsonPath("$.data.postTitle").value(post.title))
		.andExpect(jsonPath("$.data.postDescription").value(post.description))
		.andExpect(jsonPath("$.data.postContent").value(post.content));
	}*/

}
