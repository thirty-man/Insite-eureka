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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/flow")
public class FlowController {

    private final FlowService flowService;
    @PostMapping("/urlflow")
    public ResponseEntity<UrlFlowResDto> getUrlFlow(@Valid @RequestBody UrlFlowReqDto urlFlowReqDto){
        UrlFlowResDto urlFlowResDto = flowService.getUrlFlow(urlFlowReqDto);
        return new ResponseEntity<>(urlFlowResDto, HttpStatus.OK);
    }

    @PostMapping("/referrer")
    public ResponseEntity<ReferrerFlowResDto> getReferrerFlow(@Valid @RequestBody ReferrerFlowReqDto referrerFlowReqDto){
        ReferrerFlowResDto referrerFlowResDto= flowService.getReferrerFlow(referrerFlowReqDto);
        return new ResponseEntity<>(referrerFlowResDto,HttpStatus.OK);
    }

    @PostMapping("/bounce")
    public ResponseEntity<BounceResDto> getBounceCounts(@Valid @RequestBody BounceReqDto bounceReqDto){
        BounceResDto bounceResDto= flowService.getBounceCounts(bounceReqDto);
        return new ResponseEntity<>(bounceResDto,HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<ExitFlowResDto> getExitCounts(@Valid @RequestBody ExitFlowReqDto exitFlowReqDto){
        ExitFlowResDto exitFlowResDto=flowService.getExitFlow(exitFlowReqDto);
        return new ResponseEntity<>(exitFlowResDto,HttpStatus.OK);
    }

}
