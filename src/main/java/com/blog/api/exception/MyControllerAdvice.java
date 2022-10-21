package com.blog.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.security.authentication.InsufficientAuthenticationException;

import com.blog.api.util.ApiResponse;
import com.blog.api.util.ApiResponseCodeStatus;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({ApiException.class, InsufficientAuthenticationException.class})
	public ResponseEntity<ApiResponse> handleApiException(ApiException e){
		System.out.println("PASO POR MYCONTROLLER ADVICE PARA MANEJAR");
		ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, e.getMsg());

		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.NOT_FOUND
				);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String msg = error.getDefaultMessage();
			
			errors.put(fieldName, msg);
		});
		
		ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, errors);
		
		return new ResponseEntity<Object>(
					response,
					HttpStatus.BAD_REQUEST
				);
	}
	
}
