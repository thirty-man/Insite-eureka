package com.thirty.insitereadservice.buttons.controller;

import com.thirty.insitereadservice.buttons.dto.request.ButtonAbnormalReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsReqDto;
import com.thirty.insitereadservice.buttons.dto.request.EveryButtonRateReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ButtonLogsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ButtonAbnormalResDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsResDto;
import com.thirty.insitereadservice.buttons.dto.response.EveryButtonRateResDto;
import com.thirty.insitereadservice.buttons.dto.response.ButtonLogsResDto;
import com.thirty.insitereadservice.buttons.service.ButtonService;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
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
@RequestMapping("/buttons")
public class ButtonController {

    private final ButtonService buttonService;

    @PostMapping("/click-counts")
    public ResponseEntity<ClickCountsResDto> getClickCounts(
        @Valid @RequestBody ClickCountsReqDto clickCountsReqDto,
        HttpServletRequest request
    ){
      int memberId = JwtProcess.verifyAccessToken(request);//검증

        ClickCountsResDto clickCountsResDto = buttonService.getClickCounts(clickCountsReqDto, memberId);
        return new ResponseEntity<>(clickCountsResDto, HttpStatus.OK);
    }

    @PostMapping("/logs")
    public ResponseEntity<ButtonLogsResDto> getButtonLogs(
        @Valid @RequestBody ButtonLogsReqDto exitPercentageReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        ButtonLogsResDto buttonLogsResDto = buttonService.getButtonLogs(exitPercentageReqDto,
            memberId
        );
        return new ResponseEntity<>(buttonLogsResDto, HttpStatus.OK);
    }

    //모든 버튼의 평균대비 증감 횟수
    @PostMapping("/every-button-rate")
    public ResponseEntity<EveryButtonRateResDto> getEvertButtonRate(
        @Valid @RequestBody EveryButtonRateReqDto everyButtonDistReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증
        EveryButtonRateResDto everyButtonDistResDto = buttonService.getEveryButtonRate(everyButtonDistReqDto,
            memberId
        );
        return new ResponseEntity<>(everyButtonDistResDto, HttpStatus.OK);
    }


    @PostMapping("/abnormality")
    public ResponseEntity<List<ButtonAbnormalResDto>> getButtonAbnormal(
        @Valid @RequestBody ButtonAbnormalReqDto buttonAbnormalReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        List<ButtonAbnormalResDto> buttonAbnormalResDtoList = buttonService.getButtonAbnormal(buttonAbnormalReqDto, memberId);
        return new ResponseEntity<>(buttonAbnormalResDtoList, HttpStatus.OK);
    }

}
