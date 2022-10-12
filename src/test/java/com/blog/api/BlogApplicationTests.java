package com.blog.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest
@ComponentScan({ "com.blog.api" })
class BlogApplicationTests {

	@Test
	void contextLoads() {
	}

}
