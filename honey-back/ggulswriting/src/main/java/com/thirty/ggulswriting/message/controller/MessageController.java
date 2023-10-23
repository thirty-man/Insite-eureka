package com.thirty.ggulswriting.room.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirty.ggulswriting.room.dto.request.RoomParticipateReqDto;
import com.thirty.ggulswriting.room.service.RoomService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

	private final MeesageService messageService;

	@PostMapping("/")
	public ResponseEntity<String> send(
		@Valid @RequestBody MessageSendReqDto messageSendReqDto
	) {
		int participationFrom = 1;
		String result = messageService.send(messageSendReqDto, participationFrom);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
