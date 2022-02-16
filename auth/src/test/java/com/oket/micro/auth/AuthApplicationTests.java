package com.oket.micro.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
@Slf4j
class AuthApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void contextLoads() {
		String password = passwordEncoder.encode("123456");

		System.out.println(passwordEncoder.matches("123456","$2a$10$r7DhdB/EHwmIpCL3nLHAvOvBdFCuEZrOoT5rtOYCWxXnAKoqA3A5m"));
	}

}
