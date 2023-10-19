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
public class RoomServiceImpl implements RoomService {
	private final MemberRepository memberRepository;
	private final RoomRepository roomRepository;
	private final ParticipationRepository participationRepository;

	@Override
	public String participate(RoomParticipateReqDto roomParticipateReqDto, Long memberId) {
		Optional<Member> optionalMember = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(memberId);
		//탈퇴 회원 검증
		if (optionalMember.isEmpty()) {
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		//방이 존재하는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomId(roomParticipateReqDto.getRoomId());
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		//이미 참가자인지 검증
		Member member = optionalMember.get();
		Room room = optionalRoom.get();
		Optional<Participation> optionalParticipation = participationRepository.findParticipationByMemberAndRoom(member,
			room);
		if (optionalParticipation.isPresent()) {
			throw new ParticipationException(ErrorCode.ALREADY_EXIST_MEMBER);
		}
		//방장인지 검증
		Optional<Room> optionalRoomMember = roomRepository.findRoomByMember(member);
		if (optionalRoomMember.isPresent()) {
			throw new ParticipationException(ErrorCode.ALREADY_EXIST_MEMBER);
		}

		//저장
		participationRepository.save(Participation.of(member, room, false));
		return "ok";
	}
}
