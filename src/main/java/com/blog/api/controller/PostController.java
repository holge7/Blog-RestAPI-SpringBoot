package com.blog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.assembler.PostAssembler;
import com.blog.api.dto.PostDTO;
import com.blog.api.dto.PostDTOPageable;
import com.blog.api.dto.PostDTOPageableREST;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.UserRepository;
import com.blog.api.service.PostService;
import com.blog.api.util.ApiResponse;
import com.blog.api.util.ApiResponseCodeStatus;


@RestController
@RequestMapping("api/posts")
public class PostController {
	
	PostService postService;
	PostAssembler assembler;
	UserRepository userRepository;
	
	public PostController(PostService postService, UserRepository userRepository,PostAssembler assembler) {
		this.postService = postService;
		this.userRepository = userRepository;
		this.assembler = assembler;
	}
	
	/****************************** GET ZONE ******************************/
	
	@GetMapping("/test")
	public String test() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
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
	
	@GetMapping("")
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
	
	@PostMapping("")
	public ResponseEntity<ApiResponse> createPost(
			@Valid @RequestBody PostDTO newPost
			){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userRepository.findByEmail(auth.getName())
				.orElseThrow(()->new NotFoundException("") );
		
		PostDTO data = postService.create(newPost, user);

		EntityModel<PostDTO> rest = assembler.toModel(data);
		ApiResponse response = new ApiResponse(rest);
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.CREATED
				);
	}
	
	
	/****************************** DELETE ZONE ******************************/
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable long id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//Check if post is not from his user or if is admin
		if(!isOwnPost(id) && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, "Is not your post");
			return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.UNAUTHORIZED
				);
		}
		
		//Delete post
		PostDTO data = postService.deletePostById(id);		
		
		//Return response
		EntityModel<PostDTO> rest = assembler.toModel(data);
		ApiResponse response = new ApiResponse(rest);
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.ACCEPTED
				);
	}
	
	
	/****************************** PUT ZONE ******************************/
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> editPost(
			@PathVariable long id,
			@Valid @RequestBody PostDTO postEdited
			){
		
		postEdited.id=id;
		
		//Check if post is not from his user
		if(!isOwnPost(id)) {
			ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, "Is not your post");
			return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.UNAUTHORIZED
				);
		}
		
		//Edit post
		PostDTO newPostEdited = postService.editPost(postEdited);
		EntityModel<PostDTO> rest = assembler.toModel(newPostEdited);
		
		//Return response
		ApiResponse response = new ApiResponse(rest);		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
	public boolean isOwnPost(long postID) {
		//Get user register
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//Get post to do anything
		Post post = postService.findOrThrow(postID);
		
		//Check if post is from his user
		if (post.user.getEmail().equals(auth.getName())) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/test2")
	public void test2() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getCredentials());
		System.out.println(auth.getDetails());
		System.out.println(auth.getName());
		System.out.println(auth.getPrincipal());
		System.out.println(auth.getAuthorities());
	}
	
}
