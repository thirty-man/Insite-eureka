package com.thirty.insitereadservice.flow.service;

import com.thirty.insitereadservice.flow.dto.BounceDto;
import com.thirty.insitereadservice.flow.dto.request.*;
import com.thirty.insitereadservice.flow.dto.response.*;

public interface FlowService {

    EntryExitFlowResDto getEntryExitFlow(EntryExitFlowReqDto exitFlowReqDto, int memberId);
    EntryEnterFlowResDto getEntryEnterFlow(EntryEnterFlowReqDto entryEnterFlowReqDto, int memberId);
    CurrentUrlFlowResDto getUrlFlow(CurrentUrlFlowReqDto urlFlowReqDto, int memberId);
    ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto,int memberId);
    ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto,int memberId);
    BounceResDto getBounceCounts(BounceReqDto bounceReqDto, int memberId);
    BeforeUrlFlowResDto getBeforeUrlFlow(BeforeUrlFlowReqDto beforeUrlFlowReqDto, int memberId);
}
