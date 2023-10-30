package com.thirty.insitememberservice.member.repository;


import com.thirty.insitememberservice.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByKakaoIdAndGoodByeTimeIsNull(String kakaoId);

	Optional<Member> findByMemberIdAndGoodByeTimeIsNull(int memberId);
}
