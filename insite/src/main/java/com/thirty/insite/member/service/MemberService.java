package com.thirty.insite.member.service;

import javax.servlet.http.HttpServletResponse;

import com.thirty.insite.member.entity.Member;

public interface MemberService {
	Member join(String kakaoId);

	String logout(Member member);

	String reissue(Member member, String token, HttpServletResponse response);
}
