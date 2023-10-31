package com.thirty.insiteconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class InsiteConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiteConfigServiceApplication.class, args);
	}

}
