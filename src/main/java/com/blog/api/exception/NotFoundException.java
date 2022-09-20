package com.blog.api.exception;

public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String defaultMsg = "Could not found [%s]:[%s]";
	
	//Default msg
	public NotFoundException(String resource, long id) {
		super(String.format(defaultMsg, resource, id));
	}
	
	//Custom msg
	public NotFoundException(String msg) {
		super(msg);
	}
	
	
	

}
