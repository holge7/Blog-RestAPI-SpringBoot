package com.blog.api.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.blog.api.exception.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMS;
	
	//Generates the token with its algorithm and secret key
	public String tokenGeneration(Authentication authentication) {
		String username = authentication.getName();
		Date actDate = new Date();
		Date expDate = new Date(actDate.getTime() + jwtExpirationInMS);
		
		String tokenString = Jwts.builder() // Lets starts with the build
				.setSubject(username) // Set the claim "SUBJECT" of the JWT
				.setIssuedAt(new Date()) // Set the JWT claim "ISSUED AT" value
				.setExpiration(expDate) // Set the JWT claim "EXPIRATION" value
				.signWith(SignatureAlgorithm.HS512, jwtSecret) // Sings the JWT with an algorithm and key
				.compact(); // Build JWT and serializes it 
		
		return tokenString;
		
	}
	
	//Obtain the user
	public String getTokenUsername(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	//Validates that the token is correctly formed and with good data
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (SignatureException ex) {
			throw new ApiException("Not valid JWT sign");
		}catch (MalformedJwtException e) {
			throw new ApiException("Not valid Token JWT");
		}catch (ExpiredJwtException e) {
			throw new ApiException("JWT expired");
		}catch (UnsupportedJwtException e) {
			throw new ApiException("JWT not complatible");
		}catch (IllegalArgumentException e) {
			throw new ApiException("JWT claims is empty");
		}
	}
	
}
