package com.thirty.insitereadservice.currenturl.controller;

import com.thirty.insitereadservice.currenturl.dto.req.CurrentUrlListReqDto;
import com.thirty.insitereadservice.currenturl.dto.res.CurrentUrlListResDto;
import com.thirty.insitereadservice.currenturl.service.CurrentUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currenturl")
public class CurrentUrlController {
    private final CurrentUrlService currentUrlService;

    @PostMapping("/list")//해당 서비스의 InfluxDB에 누적된 모든 currentUrl을 횟수와 함께 리스트로 반환합니다.
    public ResponseEntity<CurrentUrlListResDto> getCurrentUrlList(
        @Valid @RequestBody CurrentUrlListReqDto currentUrlListReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        CurrentUrlListResDto currentUrlListResDto = currentUrlService.getCurrentUrlList(currentUrlListReqDto,memberId);
        return new ResponseEntity<>(currentUrlListResDto, HttpStatus.OK);
    }
}
