package com.thirty.insiterealtimereadservice.buttons.controller;

import com.thirty.insiterealtimereadservice.buttons.dto.request.ButtonAbnormalReqDto;
import com.thirty.insiterealtimereadservice.buttons.dto.request.ClickCountPerUserReqDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.ButtonAbnormalResDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.ClickCountPerUserResDto;
import com.thirty.insiterealtimereadservice.buttons.service.ButtonService;
import com.thirty.insiterealtimereadservice.global.jwt.JwtProcess;
import java.util.List;
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

    @PostMapping("/click-counts-per-user")
    public ResponseEntity<ClickCountPerUserResDto> clickCountPerUser(
        @Valid @RequestBody ClickCountPerUserReqDto clickCountPerUserReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증
        ClickCountPerUserResDto countPerUserResDto = buttonService.countPerUser(memberId, clickCountPerUserReqDto.getApplicationToken());
        return new ResponseEntity<>(countPerUserResDto, HttpStatus.OK);
    }

    @PostMapping("/abnormality")
    public ResponseEntity<List<ButtonAbnormalResDto>> getButtonAbnormal(
        @Valid @RequestBody ButtonAbnormalReqDto buttonAbnormalReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증
        List<ButtonAbnormalResDto> buttonAbnormalResDtoList = buttonService.getButtonAbnormal(memberId, buttonAbnormalReqDto.getApplicationToken());
        return new ResponseEntity<>(buttonAbnormalResDtoList, HttpStatus.OK);
    }
}

