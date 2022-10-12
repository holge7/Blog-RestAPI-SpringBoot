package com.blog.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.blog.api.entity.Rol;
import com.blog.api.entity.User;

@DataJpaTest
public class UserRepositoryTest {
	
	private UserRepository userRepository;
	
	public UserRepositoryTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@DisplayName("Register user")
	@Test
	void register() {
		// given
		Rol rol = new Rol(1, "ROLE_USER");
		User user1 = new User(4, "jorge", "holge", "jorge@gmail.com", "$2a$10$dmCUbulOlKe8/cIHhU1MXOuLXqYagfrZ34jhh5W7vmEwPhkBsI9oe", Collections.singleton(rol));
	
		// when
		User userSaved = this.userRepository.save(user1);
		
		// then
		assertThat(userSaved).isNotNull();
		assertThat(userSaved.getId()).isGreaterThan(0);
	
	}

}
