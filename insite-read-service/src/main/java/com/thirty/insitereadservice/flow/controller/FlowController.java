package com.thirty.insitereadservice.flow.controller;

import com.thirty.insitereadservice.flow.dto.request.*;
import com.thirty.insitereadservice.flow.dto.BounceDto;
import com.thirty.insitereadservice.flow.dto.response.*;
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

    @PostMapping("/entry-enter")
    public ResponseEntity<EntryEnterFlowResDto> getExitFlow(
        @Valid @RequestBody EntryEnterFlowReqDto entryEnterFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        EntryEnterFlowResDto entryEnterFlowResDto = flowService.getEntryEnterFlow(entryEnterFlowReqDto, memberId);
        return new ResponseEntity<>(entryEnterFlowResDto, HttpStatus.OK);
    }

    //
    @PostMapping("/urlflow")//현재 Url의 BeforeUrl을 리스트로 담아서 보내줍니다.
    public ResponseEntity<CurrentUrlFlowResDto> getUrlFlow(@Valid @RequestBody CurrentUrlFlowReqDto urlFlowReqDto,
                                                           HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        CurrentUrlFlowResDto urlFlowResDto = flowService.getUrlFlow(urlFlowReqDto,memberId);
        return new ResponseEntity<>(urlFlowResDto, HttpStatus.OK);
    }

    @PostMapping("/referrer")//해당 서비스의 외부 유입 경로를 리스트로 담아서 보내줍니다.
    public ResponseEntity<ReferrerFlowResDto> getReferrerFlow(@Valid @RequestBody ReferrerFlowReqDto referrerFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ReferrerFlowResDto referrerFlowResDto= flowService.getReferrerFlow(referrerFlowReqDto,memberId);
        return new ResponseEntity<>(referrerFlowResDto,HttpStatus.OK);
    }

    @PostMapping("/bounce")//activityId에 해당하는 활동이 한 번인 경우, 해당 Url과 횟수. 비율을 리스트로 담아 보내줍니다.
    public ResponseEntity<BounceResDto> getBounceCounts(@Valid @RequestBody BounceReqDto bounceReqDto,
                                                     HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        BounceResDto bounceResDto= flowService.getBounceCounts(bounceReqDto,memberId);
        return new ResponseEntity<>(bounceResDto,HttpStatus.OK);
    }

    @PostMapping("/exit") //모든 activityId의 마지막 Url을 총 횟수와 비율을 담아 리스트로 보내줍니다.
    public ResponseEntity<ExitFlowResDto> getExitCounts(@Valid @RequestBody ExitFlowReqDto exitFlowReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        ExitFlowResDto exitFlowResDto=flowService.getExitFlow(exitFlowReqDto,memberId);
        return new ResponseEntity<>(exitFlowResDto,HttpStatus.OK);
    }

    @PostMapping("/beforeurl") // 모든 beforeUrl을 총횟수와 함께 리스트로 담아보내줍니다. (현재 서비스에는 상관없는 컨트롤러입니다.)
    public ResponseEntity<BeforeUrlFlowResDto> getBeforeUrl(@Valid @RequestBody BeforeUrlFlowReqDto beforeUrlFlowReqDto,
                                                            HttpServletRequest request){
//           String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        BeforeUrlFlowResDto beforeUrlFlowResDto =flowService.getBeforeUrlFlow(beforeUrlFlowReqDto,memberId);
        return new ResponseEntity<>(beforeUrlFlowResDto,HttpStatus.OK);
    }
}
