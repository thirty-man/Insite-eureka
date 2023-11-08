package com.thirty.insitewriteservice.write.controller;

import com.thirty.insitewriteservice.write.service.WriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirty.insitewriteservice.write.dto.ButtonReqDto;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class WriteController {
	private final WriteService writeService;

	@PostMapping("/page")
	public ResponseEntity<String> data(@RequestBody DataReqDto dataReqDto) {
		writeService.writeData(dataReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

	@PostMapping("/button")
	public ResponseEntity<String> button(@RequestBody ButtonReqDto buttonReqDto) {
		writeService.writeButton(buttonReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}
}
