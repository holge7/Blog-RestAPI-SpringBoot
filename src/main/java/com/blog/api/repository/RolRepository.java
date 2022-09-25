package com.blog.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
	
	public Optional<Rol> findByName(String name);

}
