package com.blog.api.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMS;
	
	public String tokenGeneration(Authentication authentication) {
		String username = authentication.getName();
		Date actDate = new Date();
		Date expDate = new Date(actDate.getTime() + jwtExpirationInMS);
		
		String tokenString = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		
		return tokenString;
		
	}
	
	public String getTokenUsername(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public boolean assertToken(String token) {
		return true;
	}
	
}
