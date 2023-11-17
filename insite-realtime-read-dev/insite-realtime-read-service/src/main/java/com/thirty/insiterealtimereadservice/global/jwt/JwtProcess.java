package com.thirty.insiterealtimereadservice.global.jwt;



import com.thirty.insiterealtimereadservice.global.error.ErrorCode;
import com.thirty.insiterealtimereadservice.global.error.exception.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import javax.servlet.http.HttpServletRequest;

public class JwtProcess {

	// 액세스 토큰 검증 (return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정)
	public static int verifyAccessToken(HttpServletRequest request) {

		String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
		//받아온 토큰을 디코드 하고 검증하기
		try {
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(jwtToken);
			int memberId = decodedJWT.getClaim("memberId").asInt(); //토큰 내부는 암호화 안되어 있으니까 최소한의 정보만 넣자

			return memberId;
		} catch (TokenExpiredException e) {
			throw new TokenException(ErrorCode.EXPIRED_JWT_TOKEN);
		} catch (SignatureVerificationException e) {
			throw new TokenException(ErrorCode.NOT_VALID_TOKEN); // 서명 문제로 인한 오류
		} catch (JWTVerificationException e) {
			throw new TokenException(ErrorCode.NOT_VALID_TOKEN); // 그 외의 일반적인 JWT 검증 오류
		}
	}
}