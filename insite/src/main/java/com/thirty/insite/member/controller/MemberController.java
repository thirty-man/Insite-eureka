package com.thirty.insite.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirty.insite.global.config.auth.LoginUser;
import com.thirty.insite.global.config.jwt.JwtProcess;
import com.thirty.insite.global.config.jwt.JwtVO;
import com.thirty.insite.member.entity.Member;
import com.thirty.insite.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@AuthenticationPrincipal LoginUser loginUser) {
		Member member = loginUser.getMember();
		String JWT = memberService.logout(member);
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
