package com.blog.api.service;

import java.util.List;

import com.blog.api.dto.PostDTO;
import com.blog.api.dto.PostDTOPageable;

public interface PostService {
	
	

	public PostDTO create(PostDTO newPost);
	
	public PostDTO findByID(long id);
	
	public List<PostDTO> getAllPosts();
	
	public PostDTOPageable getPostsPageable(int indexPage, int sizePage, String sortDirection);
	
	public PostDTO deletePostById(long id);

}
