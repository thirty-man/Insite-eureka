package com.thirty.ggulswriting.room.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.room.entity.Room;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	Optional<Message> findMessageByMessageId(int messageId);
	Optional<Message> findMessageByMember(Member member);
	Optional<Message> findMessageByRoom(int roomId);
}
