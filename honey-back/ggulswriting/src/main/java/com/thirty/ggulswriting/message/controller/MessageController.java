package com.thirty.ggulswriting.room.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirty.ggulswriting.message.dto.request.MessageSendReqDto;
import com.thirty.ggulswriting.message.dto.response.MessageResDto;
import com.thirty.ggulswriting.message.service.MessageService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

	private final MessageService messageService;

	@PostMapping("/")
	public ResponseEntity<String> send(
			@Valid @RequestBody MessageSendReqDto messageSendReqDto
	) {
		String result = messageService.send(messageSendReqDto);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/{messageId}")
	public ResponseEntity<MessageResDto> read(
			@PathVariable int messageId
	) {
		int memberId = 1;
		MessageResDto response = messageService.read(messageId, memberId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
