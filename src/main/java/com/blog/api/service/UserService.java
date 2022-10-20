package com.blog.api.service;

import com.blog.api.dto.UserDTO;

public interface UserService {
	
	public UserDTO findByEmail(String email);

}
