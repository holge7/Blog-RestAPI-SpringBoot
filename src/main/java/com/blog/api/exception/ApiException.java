package com.blog.api.exception;

public class ApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public ApiException(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
