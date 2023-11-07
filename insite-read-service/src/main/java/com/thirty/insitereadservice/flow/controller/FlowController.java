package com.thirty.insitereadservice.flow.controller;

import com.thirty.insitereadservice.flow.dto.request.BounceReqDto;
import com.thirty.insitereadservice.flow.dto.request.ExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.request.ReferrerFlowReqDto;
import com.thirty.insitereadservice.flow.dto.request.UrlFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.BounceResDto;
import com.thirty.insitereadservice.flow.dto.response.ReferrerFlowResDto;
import com.thirty.insitereadservice.flow.dto.response.UrlFlowResDto;
import com.thirty.insitereadservice.flow.dto.request.EntryExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.EntryExitFlowResDto;
import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;
import com.thirty.insitereadservice.flow.service.FlowService;
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
@RequestMapping("/flow")
public class FlowController {

    private final FlowService flowService;

    @PostMapping("/entry-exit")
    public ResponseEntity<EntryExitFlowResDto> getExitFlow(
        @Valid @RequestBody EntryExitFlowReqDto exitFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        EntryExitFlowResDto entryExitFlowResDto = flowService.getEntryExitFlow(exitFlowReqDto, memberId);
        return new ResponseEntity<>(entryExitFlowResDto, HttpStatus.OK);
    }

    //
    @PostMapping("/urlflow")
    public ResponseEntity<UrlFlowResDto> getUrlFlow(@Valid @RequestBody UrlFlowReqDto urlFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        UrlFlowResDto urlFlowResDto = flowService.getUrlFlow(urlFlowReqDto,memberId);
        return new ResponseEntity<>(urlFlowResDto, HttpStatus.OK);
    }

    @PostMapping("/referrer")
    public ResponseEntity<ReferrerFlowResDto> getReferrerFlow(@Valid @RequestBody ReferrerFlowReqDto referrerFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ReferrerFlowResDto referrerFlowResDto= flowService.getReferrerFlow(referrerFlowReqDto,memberId);
        return new ResponseEntity<>(referrerFlowResDto,HttpStatus.OK);
    }

    @PostMapping("/bounce")
    public ResponseEntity<BounceResDto> getBounceCounts(@Valid @RequestBody BounceReqDto bounceReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        BounceResDto bounceResDto= flowService.getBounceCounts(bounceReqDto,memberId);
        return new ResponseEntity<>(bounceResDto,HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<ExitFlowResDto> getExitCounts(@Valid @RequestBody ExitFlowReqDto exitFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        ExitFlowResDto exitFlowResDto=flowService.getExitFlow(exitFlowReqDto,memberId);
        return new ResponseEntity<>(exitFlowResDto,HttpStatus.OK);
    }
}
