package com.blog.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class will be in charge of validationg everything that is the
 * token, that its user, password, etc. is correct and will allow us
 * to access the resources.
 * 
 * @author Jorge
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Obtain the token from the HTTP request
		String token = getJWTrequest(request);
		
		// Validate the token
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			// Obtain the username from the token
			String username = jwtTokenProvider.getTokenUsername(token);
			
			// We load the user associated to the token
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			// We establish security
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	//Bearer access token
	private String getJWTrequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			// We return the subtring without the first 7 characters because these characters are
			// "Bearer "slk342jsdnfak23 and then the bearer token
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
}
