package com.blog.api.util;

public class ApiResponse {
	
	// Code 1 -> OK
	
	public ApiResponseCodeStatus status;
	public Object data;
	public String msg;
	
	public ApiResponse() {}
	
	public ApiResponse(Object data) {
		this.data = data;
		this.status = ApiResponseCodeStatus.OK;
		this.msg = "";
	}
	
	public ApiResponse(ApiResponseCodeStatus status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	
}
