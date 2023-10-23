package com.thirty.ggulswriting.room.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirty.ggulswriting.global.error.ErrorCode;
import com.thirty.ggulswriting.global.error.exception.MemberException;
import com.thirty.ggulswriting.global.error.exception.ParticipationException;
import com.thirty.ggulswriting.global.error.exception.RoomException;
import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.member.repository.MemberRepository;
import com.thirty.ggulswriting.participation.entity.Participation;
import com.thirty.ggulswriting.participation.repository.ParticipationRepository;
import com.thirty.ggulswriting.room.dto.request.RoomParticipateReqDto;
import com.thirty.ggulswriting.room.entity.Room;
import com.thirty.ggulswriting.room.repository.RoomRepository;

import lombok.AllArgsConstructor;

@Transactional
@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final ParticipationRepository participationRepository;

	@Override
	public String participate(MessageSendReqDto messageSendReqDto, int participationFrom) {

		return "ok";
	}
}
