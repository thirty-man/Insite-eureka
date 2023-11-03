package com.thirty.insitereadservice.cumulativedata.flow.service;

import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.BounceReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.ExitFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.ReferrerFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.UrlFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.BounceResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.ExitFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.ReferrerFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.UrlFlowResDto;

public interface FlowService {
    UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto);
    ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto);
    ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto);
    BounceResDto getBounceCounts(BounceReqDto bounceReqDto);

}
