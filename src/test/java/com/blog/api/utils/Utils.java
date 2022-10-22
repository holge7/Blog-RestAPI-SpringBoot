package com.blog.api.utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.blog.api.security.JwtTokenProvider;

@Component
public class Utils {

	AuthenticationManager authenticationManager;
	JwtTokenProvider jwtTokenProvider;
	
	public Utils(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	public String getAuth(String user, String pass) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user, pass
			));
		
		return jwtTokenProvider.tokenGeneration(authentication);
	}
}
