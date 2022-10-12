package com.blog.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.blog.api.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
	
	public Optional<Rol> findByName(String name);
	
    @Modifying
    @Query(value = "TRUNCATE TABLE rol;",nativeQuery = true)
	void truncate();

}
