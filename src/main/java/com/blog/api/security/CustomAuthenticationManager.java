package com.blog.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager{
	

	private CustomUserDetailsService customUserDetailsService;
	
	public CustomAuthenticationManager(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());
		if (!passwordEncoder().matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}
	
}
