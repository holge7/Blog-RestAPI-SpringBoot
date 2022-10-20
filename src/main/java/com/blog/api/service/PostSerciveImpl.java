package com.blog.api.service;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import com.blog.api.dto.PostDTO;
import com.blog.api.dto.PostDTOPageable;
import com.blog.api.dto.UserDTO;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.PostRepository;

@Service
public class PostSerciveImpl implements PostService{
	
	private String resourceName = "Post";
	
	PostRepository postRepository;
	ModelMapper modelMapper;
	
	public PostSerciveImpl(PostRepository postRepository, ModelMapper modelMapper) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDTO create(PostDTO newPost, User user) {
		Post post = mapPostEntity(newPost);
		post.user = user;
		Post postSave = postRepository.save(post);
		return mapPostDTO(postSave);
	}
	
	@Override
	public PostDTO findByID(long id) {
		Post entityPost = findOrThrow(id);
		return mapPostDTO(entityPost);
	}
	
	@Override
	public List<PostDTO> getAllPosts(){
		List<Post> entitPosts = postRepository.findAll();
		List<PostDTO> dtoPosts = entitPosts.stream()
				.map(entity -> mapPostDTO(entity))
				.toList();
		
		return dtoPosts;
	}

	@Override
	public PostDTOPageable getPostsPageable(int indexPage, int sizePage, String sortDirection) {
		Sort sort;
		
		if (sortDirection.equals("DESC")) sort = Sort.by(Direction.DESC, "id");
		else sort = Sort.by(Direction.ASC, "id");
		
		Pageable pageable = PageRequest.of(indexPage, sizePage, sort);
		Page<Post> posts = postRepository.findAll(pageable);
		List<Post> postsList = posts.getContent();
		
		List<PostDTO> postListDTO = postsList.stream()
				.map(post -> mapPostDTO(post))
				.toList();
		
		PostDTOPageable response = new PostDTOPageable();
		response.dataPosts = postListDTO;
		response.currentPage = posts.getNumber();
		response.finalPage = posts.isLast();
		response.sizePage = sizePage;
		response.totalPages = posts.getTotalPages();
		
		return response;
	}
	
	@Override
	public PostDTO deletePostById(long id) {
		PostDTO postDTO = mapPostDTO(findOrThrow(id));
		postRepository.deleteById(id);
		
		return postDTO;
	}
	
	@Override
	public PostDTO editPost(PostDTO postEdited) {

		Post entityPost = findOrThrow(postEdited.id);
		entityPost.setTitle(postEdited.title);
		entityPost.setContent(postEdited.content);
		entityPost.setDescription(postEdited.description);
		
		Post newPost = postRepository.save(entityPost);
		
		return mapPostDTO(newPost);
	}
	
	
	
	
	
	
	
	
	/**************** UTILITIES ****************/
	
	
	public Post findOrThrow(long id) {
		Post entityPost = postRepository.findById(id)
				.orElseThrow(()->new NotFoundException(resourceName, id));
		
		return entityPost;
	}
	
	public Post mapPostEntity(PostDTO dto) {
		return modelMapper.map(dto, Post.class);
	}
	
	public PostDTO mapPostDTO(Post entity) {
		return modelMapper.map(entity, PostDTO.class);
	}


}
