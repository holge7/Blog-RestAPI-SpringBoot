package com.blog.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.assembler.PostAssembler;
import com.blog.api.dto.PostDTO;
import com.blog.api.service.PostService;


@RestController
public class TestController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	PostAssembler assembler;
	
	@GetMapping("test/{id}")
	public PostDTO getPost(@PathVariable long id){
		PostDTO postDTO = postService.findByID(id);
		assembler.toModel(postDTO);
		
		return postDTO;
	}

}
