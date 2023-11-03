package com.thirty.insitereadservice.cumulativedata.flow.controller;

import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.BounceReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.ExitFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.ReferrerFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.UrlFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.BounceResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.ExitFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.ReferrerFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.UrlFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.service.FlowService;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.global.jwt.JwtVO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/flow")
public class FlowController {

    private final FlowService flowService;
    @PostMapping("/urlflow")
    public ResponseEntity<UrlFlowResDto> getUrlFlow(@Valid @RequestBody UrlFlowReqDto urlFlowReqDto,
                                                    HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        UrlFlowResDto urlFlowResDto = flowService.getUrlFlow(urlFlowReqDto,memberId);
        return new ResponseEntity<>(urlFlowResDto, HttpStatus.OK);
    }

    @PostMapping("/referrer")
    public ResponseEntity<ReferrerFlowResDto> getReferrerFlow(@Valid @RequestBody ReferrerFlowReqDto referrerFlowReqDto,
                                                              HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        ReferrerFlowResDto referrerFlowResDto= flowService.getReferrerFlow(referrerFlowReqDto,memberId);
        return new ResponseEntity<>(referrerFlowResDto,HttpStatus.OK);
    }

    @PostMapping("/bounce")
    public ResponseEntity<BounceResDto> getBounceCounts(@Valid @RequestBody BounceReqDto bounceReqDto,
                                                        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증

        BounceResDto bounceResDto= flowService.getBounceCounts(bounceReqDto,memberId);
        return new ResponseEntity<>(bounceResDto,HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<ExitFlowResDto> getExitCounts(@Valid @RequestBody ExitFlowReqDto exitFlowReqDto,
                                                        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        ExitFlowResDto exitFlowResDto=flowService.getExitFlow(exitFlowReqDto,memberId);
        return new ResponseEntity<>(exitFlowResDto,HttpStatus.OK);
    }

}
