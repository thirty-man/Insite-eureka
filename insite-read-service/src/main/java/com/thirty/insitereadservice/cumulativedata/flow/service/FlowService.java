package com.thirty.insitereadservice.cumulativedata.flow.service;

import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.UrlFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.UrlFlowResDto;

public interface FlowService {
    UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto);
}
