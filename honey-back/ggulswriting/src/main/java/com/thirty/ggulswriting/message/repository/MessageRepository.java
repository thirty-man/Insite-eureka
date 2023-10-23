package com.thirty.ggulswriting.message.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.message.entity.Message;
import com.thirty.ggulswriting.room.entity.Room;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	Optional<Message> findMessageByMessageId(int messageId);

	List<Message> findMessagesByMember(Member member);

	List<Message> findMessagesByRoom(Room room);
}
