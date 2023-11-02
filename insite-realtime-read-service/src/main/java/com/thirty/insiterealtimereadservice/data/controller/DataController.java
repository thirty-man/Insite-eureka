package com.thirty.insiterealtimereadservice.data.controller;

import com.thirty.insiterealtimereadservice.data.dto.response.AbnormalResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.ReferrerResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.ResponseTimeResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.UserCountResDto;
import com.thirty.insiterealtimereadservice.data.service.DataService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/realtime-data")
public class DataController {

    private final DataService dataService;

    @GetMapping("/response-time")
    public ResponseEntity<ResponseTimeResDto> getResponseTime(
        @Valid @RequestParam("memberId") int memberId,
        @Valid @RequestParam("token") String token
    ){
        ResponseTimeResDto responseTimeResDto = dataService.getResponseTime(memberId, token);
        return new ResponseEntity<>(responseTimeResDto, HttpStatus.OK);
    }

    @GetMapping("/referrer")
    public ResponseEntity<ReferrerResDto> getReferrer(
        @Valid @RequestParam("memberId") int memberId,
        @Valid @RequestParam("token") String token
    ){
        ReferrerResDto referrerResDto = dataService.getReferrer(memberId, token);
        return new ResponseEntity<>(referrerResDto, HttpStatus.OK);
    }

    @GetMapping("/user-counts")
    public ResponseEntity<UserCountResDto> getUserCount(
        @Valid @RequestParam("memberId") int memberId,
        @Valid @RequestParam("token") String token
    ){
        UserCountResDto userCountResDto = dataService.getUserCount(memberId, token);
        return new ResponseEntity<>(userCountResDto, HttpStatus.OK);
    }

    @GetMapping("/abnormality")
    public ResponseEntity<AbnormalResDto> getAbnormal(
        @Valid @RequestParam("memberId") int memberId,
        @Valid @RequestParam("token") String token
    ){
        AbnormalResDto abnormalResDto = dataService.getAbnormal(memberId, token);
        return new ResponseEntity<>(abnormalResDto, HttpStatus.OK);
    }
}
