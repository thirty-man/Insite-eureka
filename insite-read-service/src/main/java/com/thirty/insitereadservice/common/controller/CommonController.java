package com.thirty.insitereadservice.common.controller;

import com.thirty.insitereadservice.common.dto.request.CommonReqDto;
import com.thirty.insitereadservice.common.dto.response.CommonResDto;
import com.thirty.insitereadservice.common.service.CommonService;
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
@RequestMapping("/common")
public class CommonController {

    private final CommonService commonService;

    @PostMapping
    public ResponseEntity<CommonResDto> getCommonInfo(
        @Valid @RequestBody CommonReqDto commonReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        CommonResDto commonResDto = commonService.getCommonInfo(commonReqDto, memberId);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }
}
