package com.thirty.insitereadservice.flow.controller;

import com.thirty.insitereadservice.flow.dto.request.ExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;
import com.thirty.insitereadservice.flow.service.FlowService;
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
@RequestMapping("/flow")
public class FlowController {

    private final FlowService flowService;

    @PostMapping("/entry-exit")
    public ResponseEntity<ExitFlowResDto> getExitFlow(
        @Valid @RequestBody ExitFlowReqDto exitFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ExitFlowResDto exitFlowResDto = flowService.getExitFlow(exitFlowReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }
}
