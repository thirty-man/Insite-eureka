package com.thirty.insitememberservice.member.service;



import com.thirty.insitememberservice.member.dto.request.MemberValidReqDto;
import com.thirty.insitememberservice.member.entity.Member;

import javax.servlet.http.HttpServletResponse;

public interface MemberService {
	Member join(String kakaoId);

	String logout(int memberId);

	String reissue(Member member, String token, HttpServletResponse response);

	void validationMemberAndApplication(int memberId, MemberValidReqDto memberValidReqDto);
}
