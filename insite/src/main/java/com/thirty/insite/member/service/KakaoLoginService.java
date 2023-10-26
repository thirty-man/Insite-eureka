package com.thirty.insite.member.service;

public interface KakaoLoginService {
	String getKakaoAccessToken(String code);

	String createKakaoUser(String token);
}
