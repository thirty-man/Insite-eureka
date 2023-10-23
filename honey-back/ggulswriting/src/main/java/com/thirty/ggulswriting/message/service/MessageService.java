package com.thirty.ggulswriting.message.service;

import com.thirty.ggulswriting.message.dto.request.MessageSendReqDto;

public interface MessageService {
	String send(MessageSendReqDto messageSendReqDto);
	String read(int messageId, int memberId);
}