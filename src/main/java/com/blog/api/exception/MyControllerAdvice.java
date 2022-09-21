package com.blog.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blog.api.util.ApiResponse;
import com.blog.api.util.ApiResponseCodeStatus;

@ControllerAdvice
public class MyControllerAdvice {
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException e){
		ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, e.getMsg());
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.NOT_FOUND
				);
	}
	
}
