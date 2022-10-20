package com.blog.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.api.dto.UserDTO;
import com.blog.api.entity.User;
import com.blog.api.exception.NotFoundException;
import com.blog.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private String RESOURCE_NAME = "User";
	
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public UserDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email)
		.orElseThrow(()->new NotFoundException(RESOURCE_NAME, email));
		
		return mapUserDTO(user);
	}
	
	
	private UserDTO mapUserDTO(User entity) {
		return modelMapper.map(entity, UserDTO.class);
	}
	
	private User mapUser(UserDTO dto) {
		return modelMapper.map(dto, User.class);
	}
}
