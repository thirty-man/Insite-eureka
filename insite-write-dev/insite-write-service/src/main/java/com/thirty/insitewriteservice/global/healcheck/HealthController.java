package com.thirty.insitewriteservice.global.healcheck;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/health")
@RestController
public class HealthController {
	@GetMapping("/check")
	public ResponseEntity<String> check(HttpServletRequest request) {
		int port = request.getServerPort();
		System.out.println("a");
		return ResponseEntity.status(HttpStatus.OK).body("port : " + port);
	}
}
