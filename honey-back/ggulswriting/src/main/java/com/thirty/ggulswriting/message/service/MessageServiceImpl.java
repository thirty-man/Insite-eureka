package com.thirty.ggulswriting.message.service;

import com.thirty.ggulswriting.global.error.exception.MemberException;
import com.thirty.ggulswriting.global.error.exception.ParticipationException;
import com.thirty.ggulswriting.global.error.exception.RoomException;
import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.member.repository.MemberRepository;
import com.thirty.ggulswriting.room.entity.Room;
import com.thirty.ggulswriting.room.repository.RoomRepository;
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

import java.util.Optional;


@Transactional
@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final MessageRepository messageRepository;
	private final ParticipationRepository participationRepository;

	@Override
	public String send(MessageSendReqDto messageSendReqDto) {
		Optional<Member> optionalMemberFrom = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(messageSendReqDto.getMemberIdFrom());
		//발신자 탈퇴 회원 검증
		if (optionalMemberFrom.isEmpty()) {
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		Optional<Member> optionalMemberTo = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(messageSendReqDto.getMemberIdTo());
		//수신자 탈퇴 회원 검증
		if (optionalMemberTo.isEmpty()) {
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		//방이 존재하는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomId(messageSendReqDto.getRoomId());
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}

		Member memberFrom = optionalMemberFrom.get();
		Member memberTo = optionalMemberTo.get();
		Room room = optionalRoom.get();

		Optional<Participation> optionalParticipationFrom = participationRepository.findParticipationByMemberAndRoomAndIsOutIsFalse(memberFrom, room);
		if (optionalParticipationFrom.isEmpty()) {
			throw new ParticipationException(ErrorCode.NOT_EXIST_PARTICIPATION);
		}
		Optional<Participation> optionalParticipationTo = participationRepository.findParticipationByMemberAndRoomAndIsOutIsFalse(memberTo, room);
		if (optionalParticipationTo.isEmpty()) {
			throw new ParticipationException(ErrorCode.NOT_EXIST_PARTICIPATION);
		}

		Participation participationFrom = optionalParticipationFrom.get();
		Participation participationTo = optionalParticipationTo.get();

		// 메세지 생성
		Message message = Message.create(participationTo, participationFrom, messageSendReqDto.getContent(), false, messageSendReqDto.getHoneyCaseType(), messageSendReqDto.getNickName());
		messageRepository.save(message);

		return "메세지 전송 완료";
	}

	@Override
	public MessageResDto read(int messageId, int memberId) {
		// 메세지 유효성 검사
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new MessageException(ErrorCode.NOT_EXIST_MESSAGE));

		// 수신자 유효성 검사
		if (message.getParticipationTo().getMember().getMemberId() != memberId) {
			throw new MessageException(ErrorCode.NOT_EXIST_MESSAGE);
		}

		// 메세지 수신
		message.markAsChecked();

		return MessageResDto.from(message);
	}
}
