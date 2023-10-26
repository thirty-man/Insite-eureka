package com.thirty.insite.global.util;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.thirty.insite.global.config.service.RedisService;

@Component
public class CustomResponseUtil {
	private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

	private final RedisService redisService;

	//의존성주입
	@Autowired
	public CustomResponseUtil(RedisService redisService) {
		this.redisService = redisService;
	}

	//여기서 토큰 담아보자
	public void success(HttpServletResponse response, int memberId) {
		try {
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(200);
			String refreshToken = response.getHeader("RefreshToken");
			redisService.setValues(String.valueOf(memberId), refreshToken);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error("서버 파싱 에러");

		}
	}

	public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus) {
		try {
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(httpStatus.value());
			response.getWriter().println(msg); // 예쁘게 메시지를 포장하는 공통적인 응답 DTO를 만들어보자!!
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			log.error("서버 파싱 에러");
		}
	}
}