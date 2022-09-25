package com.blog.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends ApiException {

	private static final long serialVersionUID = 1L;
	private static String defaultMsg = "Could not found [%s]:[%s]";
	
	//Default msg
	public NotFoundException(String resource, long id) {
		super(String.format(defaultMsg, resource, id));
	}
	
	public NotFoundException(String resource, String property) {
		super(String.format(defaultMsg, resource, property));
	}
	
	
	//Custom msg
	public NotFoundException(String msg) {
		super(msg);
	}
	
	
	

}
