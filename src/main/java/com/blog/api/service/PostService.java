package com.blog.api.service;

import java.util.List;

import com.blog.api.dto.PostDTO;
import com.blog.api.dto.PostDTOPageable;
import com.blog.api.entity.Post;
import com.blog.api.entity.User;

public interface PostService {

	public PostDTO create(PostDTO newPost, User user);
	
	public PostDTO findByID(long id);
	
	public Post findOrThrow(long id);
	
	public List<PostDTO> getAllPosts();
	
	public PostDTOPageable getPostsPageable(int indexPage, int sizePage, String sortDirection);
	
	public PostDTO deletePostById(long id);
	
	public PostDTO editPost(PostDTO postEdited);

}
