package com.thirty.ggulswriting.room.service;

import com.thirty.ggulswriting.member.dto.MemberDto;
import com.thirty.ggulswriting.room.dto.response.RoomMemberResDto;
import java.util.ArrayList;
import java.util.List;
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
	public String participate(RoomParticipateReqDto roomParticipateReqDto, int memberId) {
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

	@Override
	public void out(int roomId, int memberId) {
		Optional<Member> optionalMember = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(memberId);
		//탈퇴 회원 검증
		if (optionalMember.isEmpty()) {
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		//방이 존재하는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomId(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		//방에 참여한 사람인지 검증
		Member member = optionalMember.get();
		Room room = optionalRoom.get();
		Optional<Participation> optionalParticipation = participationRepository.findParticipationByMemberAndRoom(member,
			room);
		if (optionalParticipation.isEmpty()) {
			throw new ParticipationException(ErrorCode.NOT_EXIST_PARTICIPATION);
		}

		Participation participation = optionalParticipation.get();

		//이미 나간 사람인지 검증
		if(participation.getIsOut()){
			throw new ParticipationException(ErrorCode.ALREADY_OUT_MEMBER);
		}
		//방 나가기
		participation.out(true);
	}

	@Override
	public RoomMemberResDto getMemberList(int roomId) {
		//방이 존재하는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomId(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		Room room = optionalRoom.get();
		//삭제된 방인지 검증
		if(room.getIsDeleted()){
			throw new RoomException(ErrorCode.DELETED_ROOM);
		}

		//방에 참여한 회원 조회
		List<Participation> participationList = participationRepository.findAllByRoomAndIsOutIsFalse(room);
		List<Member> memberList = new ArrayList<>();
		for(Participation participation : participationList){
			memberList.add(participation.getMember());
		}

		//회원 id, 이름 반환
		List<MemberDto> memberDtoList = new ArrayList<>();
		for(Member member: memberList){
			memberDtoList.add(MemberDto.of(member.getMemberId(), member.getName()));
		}
		return RoomMemberResDto.from(memberDtoList);
	}
}
