package com.thirty.insitereadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import java.lang.System.Logger;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class InsiteReadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiteReadServiceApplication.class, args);
	}

	@Bean
	public Logger.Level feignLoggerLevel(){
		return Logger.Level.ALL;
	}

}
