package com.thirty.insite.global.config.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.thirty.insite.global.config.auth.LoginUser;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private final Logger log = LoggerFactory.getLogger(getClass());

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		if (isHeaderVerify(request, response)) {
			// 토큰이 존재함

			String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
			LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
			//검증하면 loginUser에 id랑 롤이 들어감 유저네임이랑 패스워드는 비어있음

			Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null
				, Collections.emptyList()); // id, role 만 존재 ,비밀번호는 null 왜냐하면 JwtProcess.verify(token)

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response); //security의 다음 필터로 이동해라

	}

	//헤더 검증 메서드
	private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
		String header = request.getHeader(JwtVO.HEADER);
		//리프레시 토큰 헤더까지 추가
		if (header == null || !header.startsWith(JwtVO.TOKEN_PREFIX)) {
			return false;
		} else {
			return true;
		}
	}
}