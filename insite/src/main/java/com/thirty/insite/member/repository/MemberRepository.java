package com.thirty.insite.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirty.insite.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberId(int memberId);

	Optional<Member> findByKakaoIdAndGoodByeTimeIsNull(String kakaoId);
}
