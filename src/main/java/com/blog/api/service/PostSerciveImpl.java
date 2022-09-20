package com.blog.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.dto.PostDTO;
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
		Post entityPost = postRepository.findById(id)
				.orElseThrow(()->new NotFoundException(resourceName, id));
		
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
