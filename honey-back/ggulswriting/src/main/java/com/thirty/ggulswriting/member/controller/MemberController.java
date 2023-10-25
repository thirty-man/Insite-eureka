package com.thirty.ggulswriting.member.controller;


import com.thirty.ggulswriting.global.config.auth.LoginUser;
import com.thirty.ggulswriting.global.config.jwt.JwtProcess;
import com.thirty.ggulswriting.global.config.jwt.JwtVO;
import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.member.service.MemberService;
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



    @PostMapping ("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal LoginUser loginUser){
        Member member = loginUser.getMember();
        String JWT = memberService.logout(member);
        return ResponseEntity.status(HttpStatus.OK).body(JWT);
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader(JwtVO.REFRESH_HEADER).replace(JwtVO.TOKEN_PREFIX,"");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);
        Member member = loginUser.getMember();
        String JWT = memberService.reissue(member,token,response);
        return ResponseEntity.status(HttpStatus.OK).body(JWT);
    }
}
