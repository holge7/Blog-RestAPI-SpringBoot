package com.blog.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.blog.api.controller.AuthController;
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.RolRepository;
import com.blog.api.repository.UserRepository;
import com.blog.api.security.JwtTokenProvider;
import com.blog.api.seeder.Seeder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthTest {

	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	Seeder seeder;
	
	@Autowired
	AuthController authController;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@BeforeEach
	public void init() {
		seeder.loadRoles();
		seeder.loadUser();
	}
	
	@Test
	@Transactional
	public void login() throws Exception {
		//Generate user
		String email = "jorge@gmail.com";
		String requeString =  "{\"usernameOrEmail\":\"jorge@gmail.com\",\"password\":\"password\"}";
		
		//Try to login
		ResultActions result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requeString));

		//Assert in response
		result.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.email").value(email));
	}
	
	@Test
	@Transactional
	public void login_bad() throws Exception {	
		//Generate user with bad password
		String requeString =  "{\"usernameOrEmail\":\"jorge@gmail.com\",\"password\":\"passwordd\"}";
		
		//Try to login
		ResultActions result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requeString));
		
		//Assert in response
		result.andExpect(status().isUnauthorized());
	}
	
	
	@Test
	@Transactional
	public void register() throws Exception {		
		//Generate and pass to json user
		User user = new User();
		user.setName("jorge");
		user.setUsername("Holge");
		user.setEmail("jorgee@gmail.com");
		user.setPassword("pasword");
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String requeJson = ow.writeValueAsString(user);
		
		//Register user
		ResultActions result = mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requeJson));
		
		//Assert in response
		result.andExpect(status().isOk())
		.andExpect(jsonPath("$.msg").value("User register successfully"));
		
		//Assert in db
		User user2 = userRepository.findByEmail("jorgee@gmail.com")
				.orElseThrow(() -> new NotFoundException("User", "jorgee@gmail.com"));

		assertEquals(user2.getEmail(), "jorgee@gmail.com");
	}
	
}
