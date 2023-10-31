package com.thirty.insitememberservice.member.controller;


import com.thirty.insitememberservice.global.config.auth.LoginUser;
import com.thirty.insitememberservice.global.config.jwt.JwtProcess;
import com.thirty.insitememberservice.global.config.jwt.JwtVO;
import com.thirty.insitememberservice.member.entity.Member;
import com.thirty.insitememberservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
		int memberId = JwtProcess.getMemberId(token);
		String JWT = memberService.logout(memberId);
		return ResponseEntity.status(HttpStatus.OK).body(JWT);
	}

	@PostMapping("/reissue")
	public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(JwtVO.REFRESH_HEADER).replace(JwtVO.TOKEN_PREFIX, "");
		LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
		Member member = loginUser.getMember();
		String JWT = memberService.reissue(member, token, response);
		return ResponseEntity.status(HttpStatus.OK).body(JWT);
	}
}
