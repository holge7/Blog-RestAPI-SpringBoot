package com.blog.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//Disable spring-security csrf, because spring boot already has its own csrf, we
		//also allow all the get requests, but the rest we will apply basic authentication
		http.csrf().disable() 
			.exceptionHandling() // Allows configuring exception handling
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement() // If a user without logging out logs in again, the first session will be closed
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext because we will control the session with JWT
			.and()
				.cors()
			.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/**").permitAll() // We authorize all request to paths GET method
			.and()
				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll() // We authorize all requests to paths that match /api/auth/**
				.anyRequest().authenticated()
			.and()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

}
