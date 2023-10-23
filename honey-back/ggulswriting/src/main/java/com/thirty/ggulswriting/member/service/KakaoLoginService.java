package com.thirty.ggulswriting.member.service;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);

    String createKakaoUser(String token);
}
