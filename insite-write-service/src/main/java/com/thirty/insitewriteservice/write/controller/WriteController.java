package com.thirty.insitewriteservice.write.controller;

import com.thirty.insitewriteservice.write.service.WriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thirty.insitewriteservice.longtime.LongtimeService;
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
	private final LongtimeService longtimeService;
	private final WriteService writeService;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/page")

	public ResponseEntity<String> data(@RequestBody DataReqDto dataReqDto) {
		writeService.writeData(dataReqDto);
		// longtimeService.writeLongData(dataReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

	@PostMapping("/button")
	public ResponseEntity<String> button(@RequestBody ButtonReqDto buttonReqDto) {
		longtimeService.writeLongButton(buttonReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}
}
