package com.thirty.ggulswriting.message.repository;

import com.thirty.ggulswriting.message.entity.Message;
import com.thirty.ggulswriting.participation.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	List<Message> findAllByParticipationTo(Participation participation);
}
