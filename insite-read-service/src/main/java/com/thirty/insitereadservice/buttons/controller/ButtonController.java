package com.thirty.insitereadservice.buttons.controller;

import com.thirty.insitereadservice.buttons.dto.request.ClickCountsPerActiveUserReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ExitPercentageReqDto;
import com.thirty.insitereadservice.buttons.dto.request.FirstClickTimeReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsPerActiveUserResDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsResDto;
import com.thirty.insitereadservice.buttons.dto.response.ExitPercentageResDto;
import com.thirty.insitereadservice.buttons.dto.response.FirstClickTimeResDto;
import com.thirty.insitereadservice.buttons.service.ButtonService;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.global.jwt.JwtVO;
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
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ClickCountsResDto clickCountsResDto = buttonService.getClickCounts(clickCountsReqDto, memberId);
        return new ResponseEntity<>(clickCountsResDto, HttpStatus.OK);
    }

    @PostMapping("/click-counts-per-active-user")
    public ResponseEntity<ClickCountsPerActiveUserResDto> getClickCountsPerUser(
        @Valid @RequestBody ClickCountsPerActiveUserReqDto clickCountsPerUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ClickCountsPerActiveUserResDto clickCountsPerActiveUserResDto = buttonService.getClickCountsPerActiveUser(
            clickCountsPerUserReqDto,
            memberId
        );
        return new ResponseEntity<>(clickCountsPerActiveUserResDto, HttpStatus.OK);
    }

    @PostMapping("/exit-after-click")
    public ResponseEntity<ExitPercentageResDto> getExitPercentage(
        @Valid @RequestBody ExitPercentageReqDto exitPercentageReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ExitPercentageResDto exitPercentageResDto = buttonService.getExitPercentage(
            exitPercentageReqDto,
            memberId
        );
        return new ResponseEntity<>(exitPercentageResDto, HttpStatus.OK);
    }

//    @PostMapping("/first-click")
//    public ResponseEntity<FirstClickTimeResDto> getFirstClickTimeAvg(
//        @Valid @RequestBody FirstClickTimeReqDto firstClickTimeReqDto,
//        HttpServletRequest request
//    ){
////        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
////        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
//        int memberId = 1;
//        FirstClickTimeResDto firstClickTimeResDto = buttonService.getFirstClickTimeAvg(
//            firstClickTimeReqDto,
//            memberId
//        );
//        return new ResponseEntity<>(firstClickTimeResDto, HttpStatus.OK);
//    }
}
