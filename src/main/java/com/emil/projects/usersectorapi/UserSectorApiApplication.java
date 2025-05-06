package com.emil.projects.usersectorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UserSectorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSectorApiApplication.class, args);
	}

}
