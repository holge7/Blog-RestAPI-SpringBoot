package com.blog.api.controller;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.dto.LoginDTO;
import com.blog.api.dto.RegisterDTO;
import com.blog.api.entity.Rol;
import com.blog.api.entity.User;
import com.blog.api.repository.RolRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.security.JwtTokenProvider;
import com.blog.api.util.ApiResponse;
import com.blog.api.util.ApiResponseCodeStatus;

/**
 * Controller for loggin and user registration
 * 
 * @author Jorge
 *
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> authenticateUser(@RequestBody LoginDTO loginDTO){

		// Validate the existence of the user/password
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()
				));

		// If it exists, we store it details in the securityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Generate a JWT
		String token = jwtTokenProvider.tokenGeneration(authentication);
		
		// We return it
		ApiResponse response = new ApiResponse();
		response.msg = "Welcome!, u are login";
		response.data = token;
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> logUser(@RequestBody RegisterDTO register){
		if (userRepository.existsByUsername(register.getName())) {
			ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, "This user name already exists");
			return new ResponseEntity<ApiResponse>(
						response,
						HttpStatus.BAD_REQUEST
					);
		}
		
		if (userRepository.existsByEmail(register.getEmail())) {
			ApiResponse response = new ApiResponse(ApiResponseCodeStatus.ERROR, "This email already exists");
			return new ResponseEntity<ApiResponse>(
						response,
						HttpStatus.BAD_REQUEST
					);
		}
		
		User user = new User();
		user.setName(register.getName());
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(register.getPassword());
		
		Rol rols = rolRepository.findByName("ROLE_USER").get();
		user.setRol(Collections.singleton(rols));
		
		userRepository.save(user);
		ApiResponse response = new ApiResponse(ApiResponseCodeStatus.OK, "User register successfully");
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
}
