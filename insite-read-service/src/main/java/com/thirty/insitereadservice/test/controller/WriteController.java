package com.thirty.insitereadservice.test.controller;

import com.thirty.insitereadservice.test.dto.AbnormalReqDto;
import com.thirty.insitereadservice.test.dto.ButtonReqDto;
import com.thirty.insitereadservice.test.dto.DataReqDto;
import com.thirty.insitereadservice.test.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/write")
public class WriteController {
    private final WriteService writeService;

    @PostMapping
    public ResponseEntity<String> write(
        @RequestBody DataReqDto DataReqDto
    ){
        writeService.writeDataToData(DataReqDto);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/button")
    public ResponseEntity<String> writeToButton(
        @RequestBody ButtonReqDto buttonReqDto
    ){
        writeService.writeDataToButton(buttonReqDto);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @PostMapping("/abnormal")
    public ResponseEntity<String> writeToAbnormal(
        @RequestBody AbnormalReqDto abnormalReqDto
    ){
        writeService.writeDataToAbnormal(abnormalReqDto);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}