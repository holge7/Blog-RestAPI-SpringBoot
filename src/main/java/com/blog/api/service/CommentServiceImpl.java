package com.blog.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.dto.CommentDTO;
import com.blog.api.entity.Comment;
import com.blog.api.entity.Post;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.CommentRepository;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService{
	
	private final String RESOURCE_NAME = "comment";
	
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostService postService;
	
	@Override
	public CommentDTO findByID(long id) {
		return mapCommentDTO(findOrThrow(id));
	}
	
	@Override
	public List<CommentDTO> findByPost(long id){
		List<Comment> comments = commentRepository.findByPostID(id);
		
		return comments.stream()
				.map(comment -> mapCommentDTO(comment))
				.toList();
	}
	
	@Override
	public List<CommentDTO> findByUser(String email){
		List<Comment> comments = commentRepository.findByEmail(email);
		
		return comments.stream()
				.map(comment -> mapCommentDTO(comment))
				.toList();
	}
	
	@Override
	public CommentDTO createComment(long postID, CommentDTO commentDTO) {
		
		Comment comment = mapCommentEntity(commentDTO);
		Post post = postService.findOrThrow(postID);
		
		comment.post = post;
		Comment newComment = commentRepository.save(comment);
		
		return mapCommentDTO(newComment);
	}
	
	@Override
	public CommentDTO editCommentDTO(long id, String body) {
		
		Comment commnetEdited = findOrThrow(id);
		commnetEdited.body = body;
		commentRepository.save(commnetEdited);
		
		return mapCommentDTO(commnetEdited);
	}
	
	@Override
	public CommentDTO deleteComment(long id) {
		Comment comment = findOrThrow(id);
		commentRepository.delete(comment);
		return mapCommentDTO(comment);
	}
	
	
	/****************************** UTILITIES ******************************/

	public Comment findOrThrow(long id) throws NotFoundException {
		 return commentRepository.findById(id)
			.orElseThrow(()->new NotFoundException(RESOURCE_NAME, id));
	}
	
	private CommentDTO mapCommentDTO(Comment comment) {
		CommentDTO dto = new CommentDTO();
		dto.id = comment.id;
		dto.name = comment.name;
		dto.email = comment.email;
		dto.body = comment.body;
		
		return dto;
	}
	
	private Comment mapCommentEntity(CommentDTO dto) {
		Comment entity = new Comment();
		entity.id = dto.id;
		entity.name = dto.name;
		entity.email = dto.email;
		entity.body = dto.body;
		
		return entity;
	}

}
