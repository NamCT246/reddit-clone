package com.namct.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {})
public class RedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditApplication.class, args);
	}

}
