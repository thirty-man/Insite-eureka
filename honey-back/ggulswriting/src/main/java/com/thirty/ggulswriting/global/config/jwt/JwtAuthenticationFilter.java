package com.thirty.ggulswriting.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thirty.ggulswriting.global.config.auth.LoginUser;
import com.thirty.ggulswriting.global.util.CustomResponseUtil;
import com.thirty.ggulswriting.member.dto.LoginReqDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private AuthenticationManager authenticationManager;

    private final CustomResponseUtil customResponseUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, CustomResponseUtil customResponseUtil){
        super(authenticationManager);
        this.customResponseUtil =customResponseUtil;
        setFilterProcessesUrl("/members/login");
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException{
        try{
            ObjectMapper om = new ObjectMapper();

            LoginReqDto loginReqDto = om.readValue(request.getInputStream(),LoginReqDto.class);
            String username = loginReqDto.getCode();
            String password = "";
            System.out.println("Jwt Authentication Filter username = "+username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,password
            );

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;
        }catch(Exception e){
            System.out.println("error");
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,HttpServletResponse response
            ,AuthenticationException failed) throws IOException, ServletException{
        customResponseUtil.fail(response,"로그인 실패", HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ,Authentication authResult)throws IOException,ServletException{
        LoginUser loginUser= (LoginUser) authResult.getPrincipal();
        String accessToken = JwtProcess.createAccessToken(loginUser);
        String refreshToken = JwtProcess.createRefreshToken(loginUser);
        response.addHeader(JwtVO.HEADER,accessToken);
        response.addHeader(JwtVO.REFRESH_HEADER,refreshToken);
        int memberId = loginUser.getMember().getMemberId();
        System.out.println("MemberId = "+loginUser.getMember().getMemberId());
        customResponseUtil.success(response,memberId);
    }
}
