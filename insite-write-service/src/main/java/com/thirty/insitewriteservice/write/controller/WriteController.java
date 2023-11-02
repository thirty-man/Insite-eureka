package com.thirty.insitewriteservice.write.controller;

import javax.validation.Valid;

import com.thirty.insitewriteservice.write.service.WriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thirty.insitewriteservice.longtime.LongtimeService;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class WriteController {
	private final LongtimeService longtimeService;
	private final WriteService writeService;

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/page")
	public ResponseEntity<String> data(@RequestBody DataReqDto dataReqDto) {
		System.out.println(dataReqDto.toString());
		writeService.writeLongData(dataReqDto);
		writeService.writeRealData(dataReqDto);

		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}

	@PostMapping("/button")
	public ResponseEntity<String> button(@RequestBody @Valid DataReqDto dataReqDto) {
		longtimeService.writeLongData(dataReqDto);
		return ResponseEntity.status(HttpStatus.OK).body("ok");
	}
}
