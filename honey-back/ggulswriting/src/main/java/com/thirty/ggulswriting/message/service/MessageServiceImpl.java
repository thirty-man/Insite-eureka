package com.thirty.ggulswriting.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirty.ggulswriting.global.error.ErrorCode;
import com.thirty.ggulswriting.global.error.exception.MessageException;
import com.thirty.ggulswriting.message.entity.Message;
import com.thirty.ggulswriting.message.dto.request.MessageSendReqDto;
import com.thirty.ggulswriting.message.dto.response.MessageResDto;
import com.thirty.ggulswriting.message.repository.MessageRepository;
import com.thirty.ggulswriting.participation.entity.Participation;
import com.thirty.ggulswriting.participation.repository.ParticipationRepository;

import lombok.AllArgsConstructor;


@Transactional
@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;
	private final ParticipationRepository participationRepository;

	@Override
	public String send(MessageSendReqDto messageSendReqDto) {
		// 발신자 유효성 검사
		Participation from = participationRepository.findById(messageSendReqDto.getParticipationFromId())
				.orElseThrow(() -> new MessageException(ErrorCode.NOT_EXIST_PARTICIPATION));

		// 수신자 유효성 검사
		Participation to = participationRepository.findById(messageSendReqDto.getParticipationToId())
				.orElseThrow(() -> new MessageException(ErrorCode.NOT_EXIST_PARTICIPATION));

		// 메세지 생성
		Message message = new Message().create(to, from, messageSendReqDto.getContent(), false, messageSendReqDto.getHoneyCaseType());
		messageRepository.save(message);

		return "메세지 전송 완료";
	}

	@Override
	public String read(Long messageId, int memberId) {
		// 메세지 유효성 검사
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new MessageException(ErrorCode.NOT_EXIST_MESSAGE));

		// 수신자 유효성 검사
		if (message.getParticipationTo().getMember().getMemberId() != memberId) {
			throw new MessageException(ErrorCode.UNAUTHORIZED_ACCESS);
		}

		// 메세지 수신
		message.setIsCheck(true);
		messageRepository.save(message);

		return MessageResponseDto.from(message);
	}
}
