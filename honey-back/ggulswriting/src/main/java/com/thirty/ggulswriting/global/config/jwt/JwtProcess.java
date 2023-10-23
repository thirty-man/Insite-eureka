package com.thirty.ggulswriting.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;


import com.thirty.ggulswriting.global.config.auth.LoginUser;
import com.thirty.ggulswriting.global.error.ErrorCode;
import com.thirty.ggulswriting.global.error.exception.TokenException;
import com.thirty.ggulswriting.member.entity.Member;

import java.util.Date;

public class JwtProcess {

    public static String createAccessToken(LoginUser loginUser){
        String jwtToken = JWT.create()
                .withSubject("honey's writing") //토큰 제목
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))
                .withClaim("memberId",loginUser.getMember().getMemberId())
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return JwtVO.TOKEN_PREFIX+jwtToken;
    }

    public static String createRefreshToken(LoginUser loginUser){
        String jwtToken = JWT.create()
                .withSubject("honey's writing")
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtVO.EXPIRATION_TIME*24*14))
                .withClaim("memberId",loginUser.getMember().getMemberId())
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return JwtVO.TOKEN_PREFIX +jwtToken;
    }

    public static LoginUser verifyAccessToken(String token){
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
            int memberId = decodedJWT.getClaim("memberId").asInt();
            Member member = Member.builder().memberId(memberId).build();
            LoginUser loginUser = new LoginUser(member);
            return loginUser;
        } catch (TokenExpiredException e){
            throw new TokenException(ErrorCode.EXPIRED_JWT_TOKEN);//만료된 토큰
        } catch (SignatureVerificationException e){
            throw new TokenException(ErrorCode.NOT_VALID_TOKEN);//서명 문제
        } catch (JWTVerificationException e){
            throw new TokenException(ErrorCode.NOT_VALID_TOKEN);//그 외 JWT 검증 오류
        }
    }
}
