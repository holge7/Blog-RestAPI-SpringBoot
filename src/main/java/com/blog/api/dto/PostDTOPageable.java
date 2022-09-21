package com.blog.api.dto;

import java.util.List;

public class PostDTOPageable {
	
	public int currentPage;
	public int sizePage;
	public List<PostDTO> dataPosts;
	public int totalPages;
	public boolean finalPage;
	
	public PostDTOPageable() {}

	public PostDTOPageable(int currentPage, int sizePage, List<PostDTO> dataPosts, int totalPages, boolean finalPage) {
		this.currentPage = currentPage;
		this.sizePage = sizePage;
		this.dataPosts = dataPosts;
		this.totalPages = totalPages;
		this.finalPage = finalPage;
	}
	
}
