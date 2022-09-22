package com.blog.api.dto;

import java.util.List;

import org.springframework.hateoas.EntityModel;

public class PostDTOPageableREST {
	
	public int currentPage;
	public int sizePage;
	public List<EntityModel<PostDTO>> dataPosts;
	public int totalPages;
	public boolean finalPage;
	
	public PostDTOPageableREST() {}

	public PostDTOPageableREST(int currentPage, int sizePage, List<EntityModel<PostDTO>> dataPosts, int totalPages, boolean finalPage) {
		this.currentPage = currentPage;
		this.sizePage = sizePage;
		this.dataPosts = dataPosts;
		this.totalPages = totalPages;
		this.finalPage = finalPage;
	}
}
