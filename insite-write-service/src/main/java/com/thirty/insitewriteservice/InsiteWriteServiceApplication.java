package com.thirty.insitewriteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class InsiteWriteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiteWriteServiceApplication.class, args);
	}

}
