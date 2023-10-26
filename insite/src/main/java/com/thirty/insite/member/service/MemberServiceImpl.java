package com.thirty.insite.member.service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thirty.insite.global.config.auth.LoginUser;
import com.thirty.insite.global.config.jwt.JwtProcess;
import com.thirty.insite.global.config.jwt.JwtVO;
import com.thirty.insite.global.config.service.RedisService;
import com.thirty.insite.member.entity.Member;
import com.thirty.insite.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final RedisService redisService;

	@Transactional
	@Override
	public Member join(String kakaoId) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("");
		Member member = Member.builder()
			.password(password)
			.kakaoId(kakaoId)
			.build();
		Member savedMamber = memberRepository.save(member);
		return savedMamber;
	}

	@Override
	public String logout(Member member) {
		String memberIdStr = Integer.toString(member.getMemberId());
		if (redisService.getValues(memberIdStr) != null) {
			redisService.expireValues(memberIdStr);
		}
		return "ok";
	}

	@Override
	public String reissue(Member member, String token, HttpServletResponse response) {
		String memberIdStr = Integer.toString(member.getMemberId());
		if (redisService.getValues(memberIdStr) == null) {
			return "no";
		} else {
			LoginUser loginUser = JwtProcess.verifyAccessToken(token);
			String Jwt = JwtProcess.createAccessToken(loginUser);
			response.addHeader(JwtVO.HEADER, Jwt);
			return "ok";
		}
	}

}
