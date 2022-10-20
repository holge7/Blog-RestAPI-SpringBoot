package com.blog.api.posts;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class PostsTests {
	
	@BeforeAll
	public static void setUp() {
		System.out.println("Before ALL");
		System.out.println("===================================================");
	}
	
	@BeforeEach
	public void prepare() {
		System.out.println("Before EACH");
	}
	
	@Test
	public void test() {
		System.out.println("T1");
	}
	
	@Test
	public void test2() {
		System.out.println("T2");
	}
	@Test
	public void test3() {
		System.out.println("T3");
	}
	
	
}
