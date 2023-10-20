package com.thirty.ggulswriting.room.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	Optional<Room> findRoomByRoomId(int roomId);

	Optional<Room> findRoomByMember(Member member);
}
