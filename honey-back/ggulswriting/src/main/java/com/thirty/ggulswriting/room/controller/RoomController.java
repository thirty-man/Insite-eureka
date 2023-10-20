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
@RequestMapping("/rooms")
public class RoomController {

	private final RoomService roomService;

	@PostMapping("/participate")
	public ResponseEntity<String> participate(
		@Valid @RequestBody RoomParticipateReqDto roomParticipateReqDto
	) {
		int memberId = 1;
		String result = roomService.participate(roomParticipateReqDto, memberId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PatchMapping("/{roomId}/out")
	public ResponseEntity<Void> out(
		@Valid @PathVariable int roomId
	){
		int memberId = 1;
		roomService.out(roomId,memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
