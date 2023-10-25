package com.thirty.ggulswriting.message.repository;

import java.util.List;
import java.util.Optional;

import com.thirty.ggulswriting.participation.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.message.entity.Message;
import com.thirty.ggulswriting.room.entity.Room;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	List<Message> findAllByParticipationTo(Participation participation);
}
