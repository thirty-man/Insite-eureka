package com.thirty.ggulswriting.participation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.participation.entity.Participation;
import com.thirty.ggulswriting.room.entity.Room;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
	Optional<Participation> findParticipationByMemberAndRoom(Member member, Room room);
}
