package com.thirty.insite.global.config.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thirty.insite.global.config.auth.LoginUser;
import com.thirty.insite.global.error.ErrorCode;
import com.thirty.insite.global.error.exception.TokenException;
import com.thirty.insite.member.entity.Member;

public class JwtProcess {

	// jwt 액세스 토큰 생성

	public static String createAccessToken(LoginUser loginUser) {
		String jwtToken = JWT.create()
			.withSubject("insite")//토큰의 제목
			//1시간
			.withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))//만료시간= 헌재시간 + 유효기간
			.withClaim("memberId", loginUser.getMember().getMemberId())
			//	.withClaim("role", "member")//String이 되기 위해 + "" 붙여 주는 것 string이 들어와야 함
			.sign(Algorithm.HMAC512(JwtVO.SECRET));

		return JwtVO.TOKEN_PREFIX + jwtToken;
	}

	//리프레시 토큰 생성 (손대는중 deviceUuid도 넣는중)
	public static String createRefreshToken(LoginUser loginUser) {
		//    public static String createRefreshToken(LoginReqDto loginre) {
		String jwtToken = JWT.create()
			.withSubject("insite")//토큰의 제목
			.withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME * 24 * 14))//만료시간= 헌재시간 + 유효기간
			.withClaim("memberId", loginUser.getMember().getMemberId())
			.sign(Algorithm.HMAC512(JwtVO.SECRET));

		return JwtVO.TOKEN_PREFIX + jwtToken;
	}

	// 액세스 토큰 검증 (return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정)
	public static LoginUser verifyAccessToken(String token) {
		//받아온 토큰을 디코드 하고 검증하기
		try {
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
			int memberId = decodedJWT.getClaim("memberId").asInt(); //토큰 내부는 암호화 안되어 있으니까 최소한의 정보만 넣자
			Member member = Member.builder().memberId(memberId).build();//user이넘 타입으로 바뀌어서 들어감
			//실제 role에는 "CUSTOMER" 이런식으로 저장 되어 있었을 것임
			LoginUser loginUser = new LoginUser(member);
			return loginUser;
		} catch (TokenExpiredException e) {
			throw new TokenException(ErrorCode.EXPIRED_JWT_TOKEN);
		} catch (SignatureVerificationException e) {
			throw new TokenException(ErrorCode.NOT_VALID_TOKEN); // 서명 문제로 인한 오류
		} catch (JWTVerificationException e) {
			throw new TokenException(ErrorCode.NOT_VALID_TOKEN); // 그 외의 일반적인 JWT 검증 오류
		}

	}

}