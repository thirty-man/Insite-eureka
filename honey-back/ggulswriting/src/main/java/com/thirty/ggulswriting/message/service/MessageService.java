package com.thirty.ggulswriting.message.service;

import com.thirty.ggulswriting.message.dto.request.MessageSendReqDto;
import com.thirty.ggulswriting.message.dto.response.MessageResDto;

public interface MessageService {
	String send(MessageSendReqDto messageSendReqDto, int memberId);
	MessageResDto read(int messageId, int memberId);
}