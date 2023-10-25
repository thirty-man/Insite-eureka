package com.thirty.ggulswriting.room.controller;

import com.thirty.ggulswriting.room.dto.request.RoomCreateReqDto;
import com.thirty.ggulswriting.room.dto.request.RoomDeleteReqDto;
import com.thirty.ggulswriting.room.dto.response.RoomCreateResDto;
import com.thirty.ggulswriting.room.dto.response.RoomMemberResDto;
import com.thirty.ggulswriting.room.dto.response.RoomResDto;
import com.thirty.ggulswriting.message.dto.response.MessageListResDto;
import javax.validation.Valid;

import com.thirty.ggulswriting.room.dto.response.RoomSearchResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/{roomId}/member-list")
	public ResponseEntity<RoomMemberResDto> getMemberList(
		@Valid @PathVariable int roomId
	){
		RoomMemberResDto roomMemberResDto = roomService.getMemberList(roomId);
		return new ResponseEntity<>(roomMemberResDto, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<RoomResDto> getRoomList(
	){
		int memberId = 1;
		RoomResDto roomResDto = roomService.getMyRoomList(memberId);
		return new ResponseEntity<>(roomResDto, HttpStatus.OK);
	}

	@GetMapping("/{roomId}/message-list")
	public ResponseEntity<MessageListResDto> getMessageList(
		@Valid @PathVariable int roomId
	){
		int memberId = 1;
		MessageListResDto messageListResDto = roomService.getMyMessageList(memberId, roomId);
		return new ResponseEntity<>(messageListResDto, HttpStatus.OK);
	}

	@PatchMapping("/remove")
	public ResponseEntity<Void> deleteRoom(
		@Valid @RequestBody RoomDeleteReqDto roomDeleteReqDto
	){
		int memberId = 1;
		roomService.deleteRoom(roomDeleteReqDto, memberId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<RoomCreateResDto> createRoom(
		@Valid @RequestBody RoomCreateReqDto roomCreateReqDto
	){
		int memberId = 1;
		RoomCreateResDto roomCreateResDto= roomService.createRoom(roomCreateReqDto,memberId);
		return new ResponseEntity<>(roomCreateResDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<RoomSearchResDto> searchRoom(
		@Valid @RequestParam("title") String title,
		@Valid @RequestParam("title") int page
	){
		return new ResponseEntity<>(roomService.searchRoom(title, page), HttpStatus.OK);
	}
}
