package com.thirty.insitereadservice.users.controller;

import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.global.jwt.JwtVO;
import com.thirty.insitereadservice.users.dto.request.AbnormalHistoryReqDto;
import com.thirty.insitereadservice.users.dto.response.AbnormalHistoryResDto;
import com.thirty.insitereadservice.users.service.UsersService;
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
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/abnormality")
    public ResponseEntity<AbnormalHistoryResDto> getAbnormalHistory(
        @Valid @RequestBody AbnormalHistoryReqDto abnormalHistoryReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        AbnormalHistoryResDto exitFlowResDto = usersService.getAbnormalHistory(abnormalHistoryReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }
}
