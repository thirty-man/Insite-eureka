package com.thirty.ggulswriting.room.service;

import com.thirty.ggulswriting.member.dto.MemberDto;
import com.thirty.ggulswriting.message.entity.Message;
import com.thirty.ggulswriting.message.repository.MessageRepository;
import com.thirty.ggulswriting.room.dto.RoomDto;
import com.thirty.ggulswriting.room.dto.RoomSearchDto;
import com.thirty.ggulswriting.room.dto.request.RoomCreateReqDto;
import com.thirty.ggulswriting.room.dto.request.RoomDeleteReqDto;
import com.thirty.ggulswriting.room.dto.request.RoomModifyReqDto;
import com.thirty.ggulswriting.room.dto.response.RoomCreateResDto;
import com.thirty.ggulswriting.room.dto.response.RoomDetailResDto;
import com.thirty.ggulswriting.room.dto.response.RoomMemberResDto;
import com.thirty.ggulswriting.room.dto.response.RoomResDto;
import com.thirty.ggulswriting.message.dto.MessageListDto;
import com.thirty.ggulswriting.message.dto.response.MessageListResDto;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.thirty.ggulswriting.room.dto.response.RoomSearchResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
	@Value("${room.salt}")
	private String SALT;

	private final MemberRepository memberRepository;
	private final MessageRepository messageRepository;
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
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomParticipateReqDto.getRoomId());
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		//이미 참가자인지 검증
		Member member = optionalMember.get();
		Room room = optionalRoom.get();
		Optional<Participation> optionalParticipation = participationRepository.findParticipationByMemberAndRoom(member,
			room);

		if(optionalParticipation.isPresent() && !optionalParticipation.get().getIsOut()){
			throw new ParticipationException(ErrorCode.ALREADY_EXIST_MEMBER);
		}

		//비밀번호 검증
		String password = SALT+roomParticipateReqDto.getPassword();
		byte[] encoding = Base64.getEncoder().encode(password.getBytes());
		String encodedPassword = new String(encoding);
		if(!encodedPassword.equals(room.getPassword())){
			throw new RoomException(ErrorCode.NOT_MATCH_PASSWORD);
		}

		//100명 이상인 경우 입장 불가
		int memberCount = participationRepository.countAllByRoomAndIsOutIsFalse(room);
		if(memberCount >= 100) {
			return "room is full";
		}

		//재입장
		if(optionalParticipation.isPresent()){
			Participation participation = optionalParticipation.get();
			participation.reParticipate();
			return "ok";
		}

		//첫입장
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
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		//방에 참여한 사람인지 검증
		Member member = optionalMember.get();
		Room room = optionalRoom.get();
		Optional<Participation> optionalParticipation = participationRepository.findParticipationByMemberAndRoomAndIsOutIsFalse(member,
			room);
		if (optionalParticipation.isEmpty()) {
			throw new ParticipationException(ErrorCode.NOT_EXIST_PARTICIPATION);
		}

		Participation participation = optionalParticipation.get();

		//방장이면 권한을 넘김
		if(member.equals(room.getMember())){
			Optional<Participation> optionalOtherParticipation = participationRepository.findTopOneByIsOutIsFalse();

			//다른 참여자가 없으면 방 삭제 아니면 방장을 넘김
			if(optionalOtherParticipation.isEmpty()){
				room.delete();
			}else{
				Participation otherParticipation = optionalOtherParticipation.get();
				room.changeMaster(otherParticipation.getMember());
			}
		}
		//방 나가기
		participation.out();
	}

	@Override
	public RoomMemberResDto getMemberList(int roomId) {
		//방이 존재하는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		Room room = optionalRoom.get();

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

	@Override
	public RoomResDto getMyRoomList(int memberId) {
		Optional<Member> optionalMember = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(memberId);
		//탈퇴 회원 검증
		if (optionalMember.isEmpty()) {
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		Member member = optionalMember.get();

		//참여한 방 조회
		List<Participation> participationList = participationRepository.findAllByMemberAndIsOutIsFalse(member);
		List<Room> roomList = new ArrayList<>();
		for(Participation participation: participationList){
			roomList.add(participation.getRoom());
		}

		List<RoomDto> roomDtoList = new ArrayList<>();
		for(Room room: roomList){
			roomDtoList.add(RoomDto.from(room));
		}
		return RoomResDto.from(roomDtoList);
	}

	@Override
	public MessageListResDto getMyMessageList(int memberId, int roomId) {
		Optional<Member> optionalMember = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(memberId);
		//탈퇴 회원 검증
		if (optionalMember.isEmpty()) {
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		//방이 존재하는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		Member member = optionalMember.get();
		Room room = optionalRoom.get();

		Optional<Participation> optionalParticipation = participationRepository.findParticipationByMemberAndRoomAndIsOutIsFalse(member, room);
		if (optionalParticipation.isEmpty()) {
			throw new ParticipationException(ErrorCode.NOT_EXIST_PARTICIPATION);
		}
		Participation participation = optionalParticipation.get();

		// 메세지 가져오기
		List<Message> messages = messageRepository.findAllByParticipationTo(participation);

		// 메세지 목록 response 주기
		List<MessageListDto> messageListDtoList = new ArrayList<>();
		for (Message message : messages) {
			messageListDtoList.add(MessageListDto.from(message));
		}
		return MessageListResDto.from(messageListDtoList);
	}

	@Override
	public RoomDetailResDto getRoomDetail(int roomId) {
		//방이 살아있는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}

		Room room = optionalRoom.get();
		Boolean isOpen = true;
		if(room.getPassword() != null) isOpen = false;
		return RoomDetailResDto.of(room.getMember().getName(), room.getRoomTitle(), isOpen, room.getShowTime());
	}

	@Override
	public void modify(int roomId, int memberId, RoomModifyReqDto roomModifyReqDto) {
		//방이 살아있는지 검증
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomId);
		if (optionalRoom.isEmpty()) {
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if(optionalMember.isEmpty()){
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}
		Member member = optionalMember.get();
		Room room = optionalRoom.get();

		// 방장이 아닌 회원 검사
		if(!room.getMember().equals(member)){
			throw new MemberException(ErrorCode.NOT_HOST_MEMBER);
		}

		//방 비밀번호 인코딩
		String password = SALT+roomModifyReqDto.getPassword();
		byte[] encoding = Base64.getEncoder().encode(password.getBytes());
		String encodedPassword = new String(encoding);

		room.modify(roomModifyReqDto.getRoomTitle(),encodedPassword);
	}

	@Override
	public void deleteRoom(RoomDeleteReqDto roomDeleteReqDto, int memberId) {
		// 탈퇴한 회원인지 검증
		Optional<Member> optionalMember = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(memberId);
		if(optionalMember.isEmpty()){
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}

		// 방 유효성 검사
		Optional<Room> optionalRoom = roomRepository.findRoomByRoomIdAndIsDeletedIsFalse(roomDeleteReqDto.getRoomId());
		if(optionalRoom.isEmpty()){
			throw new RoomException(ErrorCode.NOT_EXIST_ROOM);
		}

		Member member = optionalMember.get();
		Room room = optionalRoom.get();

		// 방장이 아닌 회원 검사
		if(!room.getMember().equals(member)){
			throw new MemberException(ErrorCode.NOT_HOST_MEMBER);
		}

		// 방 삭제
		room.delete();
	}

	@Override
	public RoomCreateResDto createRoom(RoomCreateReqDto roomCreateReqDto, int memberId) {
		// 탈퇴한 회원인지 검증
		Optional<Member> optionalMember = memberRepository.findMemberByMemberIdAndGoodbyeTimeIsNull(memberId);

		if(optionalMember.isEmpty()){
			throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
		}

		Member member = optionalMember.get();

		//비밀번호 인코딩
		String password = SALT+roomCreateReqDto.getPassword();
		byte[] encoding = Base64.getEncoder().encode(password.getBytes());
		String encodedPassword = new String(encoding);

		Room room = Room.create(
				member,
				roomCreateReqDto.getRoomTitle(),
				roomCreateReqDto.getShowTime(),
				encodedPassword
		);

		roomRepository.save(room);

		participationRepository.save(Participation.of(
			member,
			room,
			false
		));
		return RoomCreateResDto.from(room.getRoomId());
	}

	@Override
	public RoomSearchResDto searchRoom(String title, int page) {
		//5개 페이지 네이션
		Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Order.desc("roomId")));

		Page<Room> roomList = roomRepository.findByRoomTitleContainsAndIsDeletedIsFalse(title, pageable);
		List<RoomSearchDto> roomSearchDtoList = new ArrayList<>();
		for(Room room: roomList){
			log.info("room ={}",room);
			int memberCount = participationRepository.countAllByRoomAndIsOutIsFalse(room);

			Boolean isOpen = true;
			if(room.getPassword() != null){
				isOpen = false;
			}

			RoomSearchDto roomListDto = RoomSearchDto.of(room.getRoomId(), room.getRoomTitle(),room.getMember().getName(),memberCount,isOpen);
			roomSearchDtoList.add(roomListDto);
		}
		return RoomSearchResDto.from(roomSearchDtoList);
	}

}
