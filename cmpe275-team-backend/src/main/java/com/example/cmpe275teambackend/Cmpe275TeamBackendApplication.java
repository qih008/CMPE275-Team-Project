package com.example.cmpe275teambackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Cmpe275TeamBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cmpe275TeamBackendApplication.class, args);
	}
}
