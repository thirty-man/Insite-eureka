package com.thirty.ggulswriting.message.controller;

import com.thirty.ggulswriting.global.config.auth.LoginUser;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

	@PostMapping
	public ResponseEntity<String> send(
			@Valid @RequestBody MessageSendReqDto messageSendReqDto,
			@AuthenticationPrincipal LoginUser loginUser

	) {
		System.out.println(messageSendReqDto.toString());
		String result = messageService.send(messageSendReqDto, loginUser.getMember().getMemberId());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/{messageId}")
	public ResponseEntity<MessageResDto> read(
		@PathVariable int messageId,
		@AuthenticationPrincipal LoginUser loginUser
	) {
		MessageResDto response = messageService.read(messageId, loginUser.getMember().getMemberId());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
