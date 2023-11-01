package com.thirty.insiterealtimereadservice.button.controller;

import com.thirty.insiterealtimereadservice.button.dto.response.ButtonResDto;
import com.thirty.insiterealtimereadservice.button.dto.response.RealTimeCountResDto;
import com.thirty.insiterealtimereadservice.button.measurement.Button;
import com.thirty.insiterealtimereadservice.button.service.ButtonService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    public ResponseEntity<RealTimeCountResDto> readRealTimeCount(
        @Valid @RequestParam("token") String serviceToken,
        @Valid @RequestParam("name") String name
    ){
        RealTimeCountResDto realTimeCountResDto = buttonService.readRealTimeCount(serviceToken, name);
        return new ResponseEntity<>(realTimeCountResDto, HttpStatus.OK);
    }
}

