package com.thirty.insitewriteservice.write.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirty.insitewriteservice.longtime.LongtimeService;
import com.thirty.insitewriteservice.realtime.service.RealtimeService;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class WriteController {

	private final RealtimeService realtimeService;

	private final LongtimeService longtimeService;

	@PostMapping("/page")
	public ResponseEntity<String> data(@RequestBody @Valid DataReqDto dataReqDto) {
		realtimeService.writeRealData(dataReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

	@PostMapping("/button")
	public ResponseEntity<String> button(@RequestBody @Valid DataReqDto dataReqDto) {
		longtimeService.writeLongData(dataReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}
}
