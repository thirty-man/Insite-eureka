package com.thirty.insitegatewayservice.global.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtVO {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTime}")
    private int expirationTime;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.refreshHeader}")
    private String refreshHeader;
}
