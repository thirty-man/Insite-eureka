package com.thirty.ggulswriting.member.service;

import com.thirty.ggulswriting.member.entity.Member;

import javax.servlet.http.HttpServletResponse;

public interface MemberService {
    Member join(String kakaoId);

    String logout(Member member);

    String reissue(Member member, String token, HttpServletResponse response);

}
