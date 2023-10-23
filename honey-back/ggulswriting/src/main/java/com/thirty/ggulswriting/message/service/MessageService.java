package com.thirty.ggulswriting.room.service;

import com.thirty.ggulswriting.room.dto.request.RoomParticipateReqDto;

public interface MessageService {
	String send(MessageSendReqDto messageSendReqDto, int participationFrom);

}
