package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.assembler.PostAssembler;
import com.blog.api.dto.PostDTO;
import com.blog.api.dto.PostDTOPageable;
import com.blog.api.dto.PostDTOPageableREST;
import com.blog.api.exception.ApiException;
import com.blog.api.service.PostService;
import com.blog.api.util.ApiResponse;


@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	PostAssembler assembler;
	
	/****************************** GET ZONE ******************************/
	
	@GetMapping("/test")
	public String test() {
		return "test passed :)";
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getPost(@PathVariable long id){
		PostDTO postDTO = postService.findByID(id);
		
		//Build rest service
		EntityModel<PostDTO> rest = assembler.toModel(postDTO);
		
		ApiResponse response = new ApiResponse(rest);

		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAll(){
		List<PostDTO> allPosts = postService.getAllPosts();
		
		List<EntityModel<PostDTO>> postsRest = allPosts.stream()
				.map(post -> assembler.toModel(post))
				.toList();
		
		ApiResponse response = new ApiResponse(postsRest);
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<ApiResponse> getAllPageable(
			@RequestParam int indexPage,
			@RequestParam int sizePage,
			@RequestParam(defaultValue = "ASC", required = false) String sortDirection) {
		PostDTOPageable postPageable = postService.getPostsPageable(indexPage, sizePage, sortDirection);
		
		List<EntityModel<PostDTO>> postsAssembler = postPageable.dataPosts.stream()
													.map(post -> assembler.toModel(post))
													.toList();
		
		PostDTOPageableREST data = new PostDTOPageableREST();
		data.currentPage = postPageable.currentPage;
		data.dataPosts = postsAssembler;
		data.finalPage  = postPageable.finalPage;
		data.sizePage = postPageable.sizePage;
		data.totalPages = postPageable.totalPages;
		
		ApiResponse response = new ApiResponse(data);
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}

	
	
	/****************************** POST ZONE ******************************/
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createPost(@RequestBody PostDTO newPost){
		PostDTO data = postService.create(newPost);
		
		EntityModel<PostDTO> rest = assembler.toModel(data);
		ApiResponse response = new ApiResponse(rest);
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.CREATED
				);
	}
	
	
	
	/****************************** DELETE ZONE ******************************/
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable long id){
		
		PostDTO data = postService.deletePostById(id);		
		
		EntityModel<PostDTO> rest = assembler.toModel(data);
		ApiResponse response = new ApiResponse(rest);
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
}
