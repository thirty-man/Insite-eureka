package com.thirty.insiterealtimereadservice.buttons.controller;

import com.thirty.insiterealtimereadservice.buttons.dto.request.ClickCountPerUserReqDto;
import com.thirty.insiterealtimereadservice.buttons.dto.request.CountReqDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.CountPerUserResDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.CountResDto;
import com.thirty.insiterealtimereadservice.buttons.service.ButtonService;
import com.thirty.insiterealtimereadservice.global.jwt.JwtProcess;
import com.thirty.insiterealtimereadservice.global.jwt.JwtVO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/realtime-buttons")
public class ButtonController {

    private final ButtonService buttonService;

    @PostMapping("/click-counts")
    public ResponseEntity<CountResDto> clickCount(
        @Valid @RequestBody CountReqDto countReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        CountResDto countResDto = buttonService.count(memberId, countReqDto.getToken());
        return new ResponseEntity<>(countResDto, HttpStatus.OK);
    }

    @PostMapping("/click-counts-per-user")
    public ResponseEntity<CountPerUserResDto> clickCountPerUser(
        @Valid @RequestBody ClickCountPerUserReqDto clickCountPerUserReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        CountPerUserResDto countPerUserResDto = buttonService.countPerUser(memberId, clickCountPerUserReqDto.getToken());
        return new ResponseEntity<>(countPerUserResDto, HttpStatus.OK);
    }
}

