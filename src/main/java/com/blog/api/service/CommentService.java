package com.blog.api.service;

import com.blog.api.dto.CommentDTO;
import com.blog.api.entity.User;

import java.util.List;

public interface CommentService {
	
	public CommentDTO findByID(long id);
	
	public List<CommentDTO> findByPost(long id);
	
	public List<CommentDTO> findByUser(String email);
	
	public CommentDTO createComment(long postID, CommentDTO commentDTO, User user);
	
	public CommentDTO editCommentDTO(long id, String body);
	
	public CommentDTO deleteComment(long id);
	
	
}
