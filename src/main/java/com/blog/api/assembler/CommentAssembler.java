package com.blog.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.blog.api.controller.CommentController;
import com.blog.api.dto.CommentDTO;

@Component
public class CommentAssembler implements RepresentationModelAssembler<CommentDTO, EntityModel<CommentDTO>> {

	@Override
	public EntityModel<CommentDTO> toModel(CommentDTO comment) {
		return EntityModel.of(comment,
					linkTo(methodOn(CommentController.class).getCommentID(comment.id)).withSelfRel(),
					linkTo(methodOn(CommentController.class).getCommentByPost(comment.postID)).withRel("commentOfPost"),
					linkTo(methodOn(CommentController.class).getCommentByUser(comment.email)).withRel("yourComments"),
					linkTo(methodOn(CommentController.class).deleteComment(comment.id)).withRel("deleteSelf")	
				);
	}
	
}
