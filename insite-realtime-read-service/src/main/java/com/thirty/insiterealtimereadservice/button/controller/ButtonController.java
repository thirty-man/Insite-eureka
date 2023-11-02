package com.thirty.insiterealtimereadservice.button.controller;

import com.influxdb.client.InfluxDBClient;
import com.thirty.insiterealtimereadservice.button.dto.response.CountPerUserResDto;
import com.thirty.insiterealtimereadservice.button.dto.response.CountResDto;
import com.thirty.insiterealtimereadservice.button.service.ButtonService;
import java.util.Map;
import javax.annotation.Resource;
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
@RequestMapping("/realtime-buttons")
public class ButtonController {

    private final ButtonService buttonService;



    @GetMapping("/click-counts")
    public ResponseEntity<CountResDto> clickCount(
        @Valid @RequestParam("memberId") int memberId,
        @Valid @RequestParam("token") String token
    ){
        CountResDto countResDto = buttonService.count(memberId, token);
        return new ResponseEntity<>(countResDto, HttpStatus.OK);
    }

    @GetMapping("/click-counts-per-user")
    public ResponseEntity<CountPerUserResDto> clickCountPerUser(
        @Valid @RequestParam("memberId") int memberId,
        @Valid @RequestParam("token") String token
    ){
        CountPerUserResDto countPerUserResDto = buttonService.countPerUser(memberId, token);
        return new ResponseEntity<>(countPerUserResDto, HttpStatus.OK);
    }
}

