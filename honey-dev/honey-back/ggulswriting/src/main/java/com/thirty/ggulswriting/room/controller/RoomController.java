package com.thirty.ggulswriting.room.controller;

import com.thirty.ggulswriting.global.config.auth.LoginUser;
import com.thirty.ggulswriting.room.dto.request.RoomCreateReqDto;
import com.thirty.ggulswriting.room.dto.request.RoomDeleteReqDto;
import com.thirty.ggulswriting.room.dto.request.RoomModifyReqDto;
import com.thirty.ggulswriting.room.dto.request.RoomParticipateReqDto;
import com.thirty.ggulswriting.room.dto.response.RoomCreateResDto;
import com.thirty.ggulswriting.room.dto.response.RoomDetailResDto;
import com.thirty.ggulswriting.room.dto.response.RoomMemberResDto;
import com.thirty.ggulswriting.room.dto.response.RoomResDto;
import com.thirty.ggulswriting.message.dto.response.MessageListResDto;
import com.thirty.ggulswriting.room.dto.response.RoomSearchResDto;
import com.thirty.ggulswriting.room.service.RoomService;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/rooms")
@Slf4j
public class RoomController {

	private final RoomService roomService;

	@PostMapping("/participate")
	public ResponseEntity<String> participate(
		@Valid @RequestBody RoomParticipateReqDto roomParticipateReqDto,
		@AuthenticationPrincipal LoginUser loginUser
	) {
		String result = roomService.participate(roomParticipateReqDto, loginUser.getMember().getMemberId());
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PatchMapping("/{roomId}/out")
	public ResponseEntity<Void> out(
		@Valid @PathVariable int roomId,
		@AuthenticationPrincipal LoginUser loginUser
	){
		roomService.out(roomId, loginUser.getMember().getMemberId());
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
		@AuthenticationPrincipal LoginUser loginUser
	){
		RoomResDto roomResDto = roomService.getMyRoomList(loginUser.getMember().getMemberId());
		log.info("memberId ={}",loginUser.getMember().getMemberId());
		return new ResponseEntity<>(roomResDto, HttpStatus.OK);
	}

	@GetMapping("/{roomId}/message-list")
	public ResponseEntity<MessageListResDto> getMessageList(
		@Valid @PathVariable int roomId,
		@AuthenticationPrincipal LoginUser loginUser
	){
		MessageListResDto messageListResDto = roomService.getMyMessageList(loginUser.getMember().getMemberId(), roomId);
		return new ResponseEntity<>(messageListResDto, HttpStatus.OK);
	}

	@GetMapping("/{roomId}")
	public ResponseEntity<RoomDetailResDto> roomDetail(
		@Valid @PathVariable int roomId
	){
		RoomDetailResDto roomDetailResDto = roomService.getRoomDetail(roomId);
		return new ResponseEntity<>(roomDetailResDto, HttpStatus.OK);
	}

	@PatchMapping("/remove")
	public ResponseEntity<Void> deleteRoom(
		@Valid @RequestBody RoomDeleteReqDto roomDeleteReqDto,
		@AuthenticationPrincipal LoginUser loginUser
	){
		roomService.deleteRoom(roomDeleteReqDto, loginUser.getMember().getMemberId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<RoomCreateResDto> createRoom(
		@Valid @RequestBody RoomCreateReqDto roomCreateReqDto,
		@AuthenticationPrincipal LoginUser loginUser
	){
		RoomCreateResDto roomCreateResDto= roomService.createRoom(roomCreateReqDto,
			loginUser.getMember().getMemberId());
		return new ResponseEntity<>(roomCreateResDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<RoomSearchResDto> searchRoom(
		@Valid @RequestParam("title") String title,
		@Valid @RequestParam("page") int page
	){
		return new ResponseEntity<>(roomService.searchRoom(title, page), HttpStatus.OK);
	}

	@PatchMapping("/{roomId}/update")
	public ResponseEntity<Void> modifyRoom(
		@Valid @PathVariable int roomId,
		@AuthenticationPrincipal LoginUser loginUser,
		@Valid @RequestBody RoomModifyReqDto roomModifyReqDto
	){
		roomService.modify(roomId, loginUser.getMember().getMemberId(), roomModifyReqDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
