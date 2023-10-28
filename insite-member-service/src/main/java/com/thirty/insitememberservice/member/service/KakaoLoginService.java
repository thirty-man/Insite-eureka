package com.thirty.insitememberservice.member.service;

public interface KakaoLoginService {
	String getKakaoAccessToken(String code);

	String createKakaoUser(String token);
}
