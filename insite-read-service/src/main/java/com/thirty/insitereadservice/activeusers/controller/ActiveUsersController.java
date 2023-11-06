package com.thirty.insitereadservice.activeusers.controller;

import com.thirty.insitereadservice.activeusers.dto.request.ActiveUsersPerTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUsersPerTimeResDto;
import com.thirty.insitereadservice.activeusers.service.ActiveusersService;
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
@RequestMapping("/active-users")
public class ActiveUsersController {

    private final ActiveusersService activeusersService;

    @PostMapping("/active-users-per-time")
    public ResponseEntity<ActiveUsersPerTimeResDto> getActiveUsersPerTime(
        @Valid @RequestBody ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        ActiveUsersPerTimeResDto exitFlowResDto = activeusersService.getActiveUsersPerTime(activeUsersPerTimeReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }
}
