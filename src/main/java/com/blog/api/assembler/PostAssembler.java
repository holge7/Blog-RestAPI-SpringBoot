package com.blog.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.blog.api.controller.PostController;
import com.blog.api.dto.PostDTO;

@Component
public class PostAssembler implements RepresentationModelAssembler<PostDTO, EntityModel<PostDTO>>{

	@Override
	public EntityModel<PostDTO> toModel(PostDTO post) {
		return EntityModel.of(post,
				linkTo(methodOn(PostController.class).getPost(post.id)).withSelfRel(),
				linkTo(methodOn(PostController.class).deletePost(post.id)).withRel("deleteSelf"),
				linkTo(methodOn(PostController.class).editPost(post.id, post)).withRel("edit")
			);
	}
}
