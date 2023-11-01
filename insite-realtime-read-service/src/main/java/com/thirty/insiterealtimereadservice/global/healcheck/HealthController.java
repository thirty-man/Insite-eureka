package com.thirty.insiterealtimereadservice.global.healcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/health")
@RestController
public class HealthController {
	@GetMapping("/check")
	public ResponseEntity<String> check() {
		return ResponseEntity.status(HttpStatus.OK).body("health check ok");
	}
}
