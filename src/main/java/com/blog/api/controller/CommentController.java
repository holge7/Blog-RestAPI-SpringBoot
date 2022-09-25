package com.blog.api.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.assembler.CommentAssembler;
import com.blog.api.dto.CommentDTO;
import com.blog.api.service.CommentService;
import com.blog.api.util.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentAssembler commentAssembler;
	
	
	/****************************** GET ZONE ******************************/
	
	@GetMapping("/{commentID}")
	public ResponseEntity<ApiResponse> getCommentID(
			@PathVariable(value = "commentID") long commentID){
		
		CommentDTO dto = commentService.findByID(commentID);
		
		return generateResponse(dto, HttpStatus.OK);
	}
	
	@GetMapping("/post/{postID}")
	public ResponseEntity<ApiResponse> getCommentByPost(
			@PathVariable(value = "postID") long postID){
		List<CommentDTO> dtos = commentService.findByPost(postID);
		
		return generateResponse(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/user/{email}")
	public ResponseEntity<ApiResponse> getCommentByUser(
			@PathVariable(value = "email") String email){
		List<CommentDTO> dtos = commentService.findByUser(email);
		
		return generateResponse(dtos, HttpStatus.OK);
	}
	
	/****************************** DELETE ZONE ******************************/
	
	@DeleteMapping("/{commentID}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable(value = "commentID") long commentID){
		
		CommentDTO dto = commentService.deleteComment(commentID);
		
		return generateResponse(dto, HttpStatus.OK);
	}
	
	
	
	/****************************** POST ZONE ******************************/
	
	@PostMapping("/{postID}")
	public ResponseEntity<ApiResponse> saveComment(
			@PathVariable(value = "postID") long postID,
			@Valid @RequestBody CommentDTO commentDTO){
		
		CommentDTO dto = commentService.createComment(postID, commentDTO);
		
		return generateResponse(dto, HttpStatus.OK);
	}
	
	
	
	
	
	/****************************** PUT ZONE ******************************/
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<ApiResponse> editComment(
				@PathVariable(value = "id") long id,
				@RequestBody Map<String, String> data
			){
		
		CommentDTO dto = commentService.editCommentDTO(id, data.get("body"));
		
		return generateResponse(dto, HttpStatus.OK);
	}
	
	
	
	/****************************** UTILITIES ******************************/
	
	private ResponseEntity<ApiResponse> generateResponse(CommentDTO data, HttpStatus httpStatus){

		EntityModel<CommentDTO> rest = commentAssembler.toModel(data);
		ApiResponse response = new ApiResponse(rest);
		
		return new ResponseEntity<ApiResponse>(
					response, 
					httpStatus
				);
	}
	
	private ResponseEntity<ApiResponse> generateResponse(List<CommentDTO> data, HttpStatus httpStatus){
		
		List<EntityModel<CommentDTO>> rest = data.stream()
				.map(comment -> commentAssembler.toModel(comment))
				.toList();
		
		ApiResponse response = new ApiResponse(rest);
		
		return new ResponseEntity<ApiResponse>(
					response, 
					httpStatus
				);
	}
	
	
}
