package com.thirty.ggulswriting.global.util;

import com.thirty.ggulswriting.global.config.service.RedisService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


@Component
public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    private final RedisService redisService;


    @Autowired
    public CustomResponseUtil(RedisService redisService){
        this.redisService = redisService;
    }

    public void success(HttpServletResponse response,int memberId){
        try{
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            String refreshToken = response.getHeader("RefreshToken");
            System.out.println("refreshToken= "+ refreshToken);
            redisService.setValues(String.valueOf(memberId),refreshToken);
        }catch(Exception e){
            System.out.println(e.getMessage());
            log.error("서버 파싱 에러");
        }
    }
    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus){
        try{
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(msg);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            log.error("서버 파싱 에러");
        }
    }


}
