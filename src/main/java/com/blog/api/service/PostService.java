package com.blog.api.service;

import java.util.List;

import com.blog.api.dto.PostDTO;

public interface PostService {

	public PostDTO create(PostDTO newPost);
	
	public PostDTO findByID(long id);
	
	public List<PostDTO> getAllPosts();

}
