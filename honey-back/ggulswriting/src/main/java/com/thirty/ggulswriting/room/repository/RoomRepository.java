package com.thirty.ggulswriting.room.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	Optional<Room> findRoomByRoomId(int roomId);

	Optional<Room> findRoomByMember(Member member);

	Optional<Room> findRoomByRoomIdAndIsDeletedIsFalse(int roomId);

	Page<Room> findByRoomTitleContainsAndIsDeletedIsFalse(String title, Pageable pageable);

	int countAllByIsDeletedIsFalse();

}
