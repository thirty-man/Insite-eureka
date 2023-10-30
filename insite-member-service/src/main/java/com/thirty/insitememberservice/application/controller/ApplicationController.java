package com.thirty.insitememberservice.application.controller;


import com.thirty.insitememberservice.application.dto.request.ApplicationCreateReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationDeleteReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationModifyReqDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationCreateResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationResDto;
import com.thirty.insitememberservice.application.service.ApplicationService;
import com.thirty.insitememberservice.application.service.ApplicationServiceImpl;
import com.thirty.insitememberservice.global.config.auth.LoginUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/{applicationId}/list")
    public ResponseEntity<ApplicationResDto> getApplicationList(
            @AuthenticationPrincipal LoginUser loginUser
    ){
        ApplicationResDto applicationResDto = applicationService.getMyApplicationList(loginUser.getMember().getMemberId());
        return new ResponseEntity<>(applicationResDto,HttpStatus.OK);
    }

//    @GetMapping("/{applicationId}/token") 토큰 조회 만들어야 함
    
    
    @PostMapping("/regist")
    public ResponseEntity<ApplicationCreateResDto> regist(@Valid @RequestBody ApplicationCreateReqDto applicationCreateReqDto,
                                                          @AuthenticationPrincipal LoginUser loginUser){
        ApplicationCreateResDto applicationCreateResDto = applicationService.regist(applicationCreateReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(applicationCreateResDto, HttpStatus.OK);
    }

    @PatchMapping("/{applicationId}/modify")
    public ResponseEntity<Void> modifyApplication(
            @Valid @PathVariable int applicationId,
            @AuthenticationPrincipal LoginUser loginUser,
            @Valid @RequestBody ApplicationModifyReqDto applicationModifyReqDto
            ){
        applicationService.modifyApplication(applicationModifyReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{applicationId}/remove")
    public ResponseEntity<Void> deleteApplication(
            @Valid @PathVariable int applicationId,
            @AuthenticationPrincipal LoginUser loginUser,
            @Valid @RequestBody ApplicationDeleteReqDto applicationDeleteReqDto
            ){
        applicationService.deleteApplication(applicationDeleteReqDto,loginUser.getMember().getMemberId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    
}
