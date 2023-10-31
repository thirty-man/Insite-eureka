package com.thirty.insitememberservice.application.controller;


import com.thirty.insitememberservice.application.dto.request.ApplicationCreateReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationDeleteReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationModifyReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationTokenReqDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationCreateResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationTokenResDto;
import com.thirty.insitememberservice.application.service.ApplicationService;
import com.thirty.insitememberservice.application.service.ApplicationServiceImpl;
import com.thirty.insitememberservice.global.config.auth.LoginUser;
import com.thirty.insitememberservice.global.config.jwt.JwtProcess;
import com.thirty.insitememberservice.global.config.jwt.JwtVO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/application")
public class ApplicationController {

    //@AuthenticationPrincipal 적용 X
//    String token = request.getHeader(JwtVO.REFRESH_HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//    LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
//    String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//    int memberId = JwtProcess.getMemberId(token);
    //헤더를 통해 들어오는 jwt 토큰을 통해 login 유저 확인할 것
    
    private final ApplicationService applicationService;

    @GetMapping("/list")
    public ResponseEntity<ApplicationResDto> getApplicationList(
            HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        ApplicationResDto applicationResDto = applicationService.getMyApplicationList(loginUser.getMember().getMemberId());
        return new ResponseEntity<>(applicationResDto,HttpStatus.OK);
    }

    @GetMapping("/{applicationId}/token")
    public ResponseEntity<ApplicationTokenResDto> readToken(@Valid @RequestBody ApplicationTokenReqDto applicationTokenReqDto,
                                                            HttpServletRequest request
                                                          ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        ApplicationTokenResDto applicationTokenResDto= applicationService.getApplicationToken(applicationTokenReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(applicationTokenResDto,HttpStatus.OK);
    }
    
    
    @PostMapping("/regist")
    public ResponseEntity<ApplicationCreateResDto> regist(@Valid @RequestBody ApplicationCreateReqDto applicationCreateReqDto,
                                                          HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        ApplicationCreateResDto applicationCreateResDto = applicationService.regist(applicationCreateReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(applicationCreateResDto, HttpStatus.OK);
    }

    @PatchMapping("/{applicationId}/modify")
    public ResponseEntity<Void> modifyApplication(
            @Valid @PathVariable int applicationId,
            @Valid @RequestBody ApplicationModifyReqDto applicationModifyReqDto,HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        applicationService.modifyApplication(applicationModifyReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{applicationId}/remove")
    public ResponseEntity<Void> deleteApplication(
            @Valid @PathVariable int applicationId,
            @Valid @RequestBody ApplicationDeleteReqDto applicationDeleteReqDto,
            HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        applicationService.deleteApplication(applicationDeleteReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
