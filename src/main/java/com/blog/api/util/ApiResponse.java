package com.blog.api.util;

public class ApiResponse {

	public ApiResponseCodeStatus status;
	public Object data;
	public Object msg;
	
	public ApiResponse() {}
	
	public ApiResponse(Object data) {
		this.data = data;
		this.status = ApiResponseCodeStatus.OK;
		this.msg = "";
	}
	
	public ApiResponse(ApiResponseCodeStatus status, Object msg) {
		this.status = status;
		this.msg = msg;
	}
	
	
}
