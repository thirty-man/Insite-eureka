package com.thirty.insite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiteApplication.class, args);
	}

}
