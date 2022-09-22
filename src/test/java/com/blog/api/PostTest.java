package com.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.api.assembler.PostAssembler;
import com.blog.api.controller.PostController;
import com.blog.api.controller.TestController;
import com.blog.api.dto.PostDTO;
import com.blog.api.service.PostService;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

@WebMvcTest(PostController.class) 
@ComponentScan({ "com.blog.api" })
public class PostTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PostService postService;
	
	private PostDTO post = new PostDTO(1, "Test", "description", "content");
	
	@Test
	void testShowDetails() throws Exception {
		when(postService.findByID(1)).thenReturn(post);
		
		mockMvc.perform(get("/test/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.postTitle").value("Test"))
			.andExpect(jsonPath("$.postDescription").value("description"));
		 
		verify(postService).findByID(1);
	}
	
	
	
}
