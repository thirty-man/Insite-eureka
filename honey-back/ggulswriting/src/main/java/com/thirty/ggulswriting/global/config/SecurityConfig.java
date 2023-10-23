package com.thirty.ggulswriting.global.config;

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import com.thirty.ggulswriting.global.util.CustomResponseUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.logging.Logger;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CustomResponseUtil customResponseUtil;

    @Autowired
    public SecurityConfig(CustomResponseUtil customResponseUtil){
        this.customResponseUtil=customResponseUtil;
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity>{

        @Override
        public void configure(HttpSecurity builder) throws Exception{
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JWTAuthentication())
        }
    }
}
