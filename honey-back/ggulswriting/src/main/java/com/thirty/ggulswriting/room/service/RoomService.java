package com.thirty.ggulswriting.room.service;

import com.thirty.ggulswriting.room.dto.request.RoomParticipateReqDto;
import com.thirty.ggulswriting.room.dto.response.RoomMemberResDto;

public interface RoomService {
	String participate(RoomParticipateReqDto roomParticipateReqDto, int memberId);

	void out(int roomId, int memberId);

	RoomMemberResDto getMemberList(int roomId);
}
