package com.blog.api.controller;

import java.util.Collections;

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
import com.blog.api.dto.UserDTO;
import com.blog.api.entity.Rol;
import com.blog.api.entity.User;
import com.blog.api.repository.RolRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.security.JwtTokenProvider;
import com.blog.api.service.UserService;
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
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RolRepository rolRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	
	public AuthController(
			AuthenticationManager authenticationManager, 
			UserRepository userRepository, 
			RolRepository rolRepository, 
			PasswordEncoder passwordEncoder, 
			JwtTokenProvider jwtTokenProvider,
			UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.rolRepository = rolRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}
	
	
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
		
		UserDTO user = userService.findByEmail(loginDTO.getUsernameOrEmail());
		user.setJwt(token);
		
		// We return it
		ApiResponse response = new ApiResponse();
		response.status = ApiResponseCodeStatus.OK;
		response.msg = "Welcome!, u are login";
		response.data = user;
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> logUser(@RequestBody RegisterDTO register){
		
		ApiResponse response = null;

		// If the userName/email already exists
		if (userRepository.existsByUsername(register.getName())) {
			response = new ApiResponse(ApiResponseCodeStatus.ERROR, "This user name already exists");
		}else if (userRepository.existsByEmail(register.getEmail())) {
			response = new ApiResponse(ApiResponseCodeStatus.ERROR, "This email already exists");
		}
		
		if (response != null) {
			return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.BAD_REQUEST
				);			
		}
		
		// Create new user
		User user = new User();
		user.setName(register.getName());
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		
		// Assign their roles
		Rol rols = rolRepository.findByName("ROLE_USER").get();
		user.setRol(Collections.singleton(rols));
		
		// Save the user
		userRepository.save(user);
		
		response = new ApiResponse(ApiResponseCodeStatus.OK, "User register successfully");
		
		return new ResponseEntity<ApiResponse>(
					response,
					HttpStatus.OK
				);
	}
	
}
