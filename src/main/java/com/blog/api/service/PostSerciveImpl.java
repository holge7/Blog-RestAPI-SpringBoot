package com.blog.api.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import com.blog.api.dto.PostDTO;
import com.blog.api.dto.PostDTOPageable;
import com.blog.api.entity.Post;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.PostRepository;

@Service
public class PostSerciveImpl implements PostService{
	
	private String resourceName = "Post";
	
	@Autowired
	PostRepository postRepository;

	@Override
	public PostDTO create(PostDTO newPost) {
		Post post = mapPostEntity(newPost);
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
	
	
	
	private Post findOrThrow(long id) {
		Post entityPost = postRepository.findById(id)
				.orElseThrow(()->new NotFoundException(resourceName, id));
		
		return entityPost;
	}
	
	public Post mapPostEntity(PostDTO dto) {
		Post entity = new Post();
		entity.setPostID(dto.postID);
		entity.setPostTitle(dto.postTitle);
		entity.setPostDescription(dto.postDescription);
		entity.setPostContent(dto.postContent);
		
		return entity;
	}
	
	public PostDTO mapPostDTO(Post entity) {
		PostDTO dto = new PostDTO();
		dto.postID = entity.getPostID();
		dto.postTitle = entity.getPostTitle();
		dto.postDescription = entity.getPostDescription();
		dto.postContent = entity.getPostContent();
		
		return dto;
	}


}
