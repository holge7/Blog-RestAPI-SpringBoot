package com.blog.api.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.UserRepository;
import com.blog.api.service.CommentService;
import com.blog.api.util.ApiResponse;
import com.blog.api.util.ApiResponseCodeStatus;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {
	

	private CommentService commentService;
	private CommentAssembler commentAssembler;
	private UserRepository userRepository;
	
	public CommentController(CommentService commentService, UserRepository userRepository, CommentAssembler commentAssembler) {
		this.commentService = commentService;
		this.commentAssembler = commentAssembler;
		this.userRepository = userRepository;
	}
	
	
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
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//Check if it his comment
		if (!isOwnComment(commentID) && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, "Is not your comment");
			return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.UNAUTHORIZED
				);
		}
		
		
		CommentDTO dto = commentService.deleteComment(commentID);
		
		return generateResponse(dto, HttpStatus.ACCEPTED);
	}
	
	
	
	/****************************** POST ZONE ******************************/
	
	@PostMapping("/{postID}")
	public ResponseEntity<ApiResponse> saveComment(
			@PathVariable(value = "postID") long postID,
			@Valid @RequestBody CommentDTO commentDTO){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userRepository.findByEmail(auth.getName())
				.orElseThrow(()->new NotFoundException("User", auth.getName()));

		
		CommentDTO dto = commentService.createComment(postID, commentDTO, user);
		
		return generateResponse(dto, HttpStatus.OK);
	}
	
	
	
	
	
	/****************************** PUT ZONE ******************************/
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> editComment(
				@PathVariable(value = "id") long id,
				@RequestBody Map<String, String> data
			){

		//Check if it his comment
		if (!isOwnComment(id)) {
			ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, "Is not your comment");
			return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.UNAUTHORIZED
				);
		}

		//Edit post
		CommentDTO dto = commentService.editCommentDTO(id, data.get("body"));
		
		//Return response
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
	
	public boolean isOwnComment(long commentID) {
		//Get user register
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//Get post to do anything
		CommentDTO commentDTO = commentService.findByID(commentID);
		
		//Check if post is from his user
		if (commentDTO.userEmail.equals(auth.getName())) {
			return true;
		}
		return false;
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
