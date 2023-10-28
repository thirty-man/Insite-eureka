package com.thirty.insitegatewayservice.global.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thirty.insitegatewayservice.global.error.ErrorCode;
import com.thirty.insitegatewayservice.global.error.exception.TokenException;

import com.thirty.insitegatewayservice.global.jwt.JwtVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//커스텀필터는 등록을 원하는 라우터에 직접 등록해야함 yml에 fillters: 필터클래스명 등록
@Component //커스텀 필터 만들기
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	private final JwtVO jwtVO;
	public AuthorizationHeaderFilter(JwtVO jwtVO) {
		super(Config.class);
		this.jwtVO = jwtVO;
	}

	@Override
	public GatewayFilter apply(Config config) {
		//custom pre filter
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			boolean header = request.getHeaders().containsKey(jwtVO.getHeader());
			if(!header){
				return onError(exchange , "토큰 값이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
			}
			String token = request.getHeaders().get(jwtVO.getHeader()).get(0).replace(jwtVO.getTokenPrefix(), "");
			verifyAccessToken(token);
			return chain.filter(exchange);
		};
	}


	private Mono<Void> onError(ServerWebExchange exchange, String s, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	public boolean verifyAccessToken(String token) {
		//받아온 토큰을 디코드 하고 검증하기
		try {
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtVO.getSecret())).build().verify(token);
			return true;
		} catch (TokenExpiredException e) {
			throw new TokenException(ErrorCode.EXPIRED_JWT_TOKEN);
		} catch (SignatureVerificationException e) {
			throw new TokenException(ErrorCode.NOT_VALID_TOKEN); // 서명 문제로 인한 오류
		} catch (JWTVerificationException e) {
			throw new TokenException(ErrorCode.NOT_VALID_TOKEN); // 그 외의 일반적인 JWT 검증 오류
		}

	}


	public static class Config {

	}
}
