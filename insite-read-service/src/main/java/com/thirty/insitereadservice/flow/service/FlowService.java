package com.thirty.insitereadservice.flow.service;

import com.thirty.insitereadservice.flow.dto.request.ExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;

public interface FlowService {

    ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto, int memberId);
}
