package com.thirty.ggulswriting.global.config.jwt;

public interface JwtVO {

    public static final String SECRET = "꿀스라이팅Honey'sWriting꿀스라이팅Honey'sWriting꿀스라이팅Honey'sWriting";//HS256
    //jwt 토큰을 암호화할 때 사용하는 키
    //노출 x

    public static final int EXPIRATION_TIME = 1000*60*60; // 1000이 1초, 1시간

    public static final String TOKEN_PREFIX = "Bearer "; // 토큰 접두사, 한 칸 띄우는 거 확인

    public static final String HEADER = "Authorization"; //헤더

    public static final String REFRESH_HEADER = "RefreshToken"; //리프레시토큰 헤더


}
