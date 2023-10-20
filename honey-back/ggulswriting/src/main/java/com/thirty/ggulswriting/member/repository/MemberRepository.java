package com.thirty.ggulswriting.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.ggulswriting.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findMemberByMemberIdAndGoodbyeTimeIsNull(Long memberId);
}
