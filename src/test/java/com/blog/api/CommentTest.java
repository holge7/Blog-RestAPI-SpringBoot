package com.blog.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.api.assembler.CommentAssembler;
import com.blog.api.controller.CommentController;
import com.blog.api.dto.CommentDTO;
import com.blog.api.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blog.api.resource.TestEnty;

@WebMvcTest(CommentController.class)
@ComponentScan("com.blog.api")
public class CommentTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CommentService commentService;
	
	@Autowired
	CommentAssembler commentAssembler;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void config() {
		objectMapper = new ObjectMapper();
	}
	
	@Test
	void findByID(long id) {
		
	}
	
	
	
	
	
}
