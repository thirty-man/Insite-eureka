package com.thirty.ggulswriting.room.repository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	Optional<Room> findRoomByRoomId(int roomId);

	Optional<Room> findRoomByMember(Member member);

	Optional<Room> findRoomByRoomIdAndIsDeletedIsFalse(int roomId);
}
