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
    UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto,int memberId);
    ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto,int memberId);
    ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto,int memberId);
    BounceResDto getBounceCounts(BounceReqDto bounceReqDto,int memberId);

}
