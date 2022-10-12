package com.blog.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.api.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{

	private RolRepository rolRepository;
	
	public RolServiceImpl(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}
	
 	@Transactional
	@Override
	public void truncatee() {
		rolRepository.truncate();
	}

}
