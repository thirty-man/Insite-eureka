package com.thirty.insiterealtimereadservice.data.controller;

import com.thirty.insiterealtimereadservice.data.dto.request.AbnormalReqDto;
import com.thirty.insiterealtimereadservice.data.dto.request.ReferrerReqDto;
import com.thirty.insiterealtimereadservice.data.dto.request.UserCountReqDto;
import com.thirty.insiterealtimereadservice.data.dto.response.AbnormalResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.ReferrerResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.UserCountResDto;
import com.thirty.insiterealtimereadservice.data.service.DataService;
import com.thirty.insiterealtimereadservice.global.jwt.JwtProcess;
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
@RequestMapping("/realtime-data")
public class DataController {

    private final DataService dataService;

    @PostMapping("/referrer")
    public ResponseEntity<ReferrerResDto> getReferrer(
        @Valid @RequestBody ReferrerReqDto referrerReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증
        ReferrerResDto referrerResDto = dataService.getReferrer(memberId, referrerReqDto.getApplicationToken());
        return new ResponseEntity<>(referrerResDto, HttpStatus.OK);
    }

    @PostMapping("/user-counts")
    public ResponseEntity<UserCountResDto> getUserCount(
        @Valid @RequestBody UserCountReqDto userCountReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증
        UserCountResDto userCountResDto = dataService.getUserCount(memberId,
            userCountReqDto.getApplicationToken());
        return new ResponseEntity<>(userCountResDto, HttpStatus.OK);
    }

    @PostMapping("/abnormality")
    public ResponseEntity<AbnormalResDto> getAbnormal(
        @Valid @RequestBody AbnormalReqDto abnormalReqDto,
        HttpServletRequest request
    ){
//        int memberId = JwtProcess.verifyAccessToken(request);//검증
        int memberId = 1;
        AbnormalResDto abnormalResDto = dataService.getAbnormal(memberId, abnormalReqDto.getApplicationToken());
        return new ResponseEntity<>(abnormalResDto, HttpStatus.OK);
    }
}
