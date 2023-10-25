package com.thirty.ggulswriting.room.service;

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

public interface RoomService {
	String participate(RoomParticipateReqDto roomParticipateReqDto, int memberId);

	void out(int roomId, int memberId);

	RoomMemberResDto getMemberList(int roomId);

	RoomResDto getMyRoomList(int memberId);

	MessageListResDto getMyMessageList(int memberId, int roomId);

	void deleteRoom(RoomDeleteReqDto roomDeleteReqDto, int memberId);

	RoomCreateResDto createRoom(RoomCreateReqDto roomCreateReqDto, int memberId);

	RoomSearchResDto searchRoom(String title, int page);

	RoomDetailResDto getRoomDetail(int roomId);

	void modify(int roomId,int memberId, RoomModifyReqDto roomModifyReqDto);
}
