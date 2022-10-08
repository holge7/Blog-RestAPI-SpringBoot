package com.blog.api.service;

import org.modelmapper.ModelMapper;
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
	
	private CommentRepository commentRepository;
	private PostService postService;
	private ModelMapper modelMapper;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostService postService, ModelMapper modelMapper){
		this.commentRepository = commentRepository;
		this.postService = postService;
		this.modelMapper = modelMapper;
	}
	
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
	
	private CommentDTO mapCommentDTO(Comment entity) {
		return modelMapper.map(entity, CommentDTO.class);
	}
	
	private Comment mapCommentEntity(CommentDTO dto) {
		return modelMapper.map(dto, Comment.class);
	}

}
