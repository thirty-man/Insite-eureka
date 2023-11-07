package com.thirty.insitereadservice.flow.service;

import com.thirty.insitereadservice.flow.dto.request.ExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.request.BounceReqDto;
import com.thirty.insitereadservice.flow.dto.request.ReferrerFlowReqDto;
import com.thirty.insitereadservice.flow.dto.request.UrlFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.BounceResDto;
import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;
import com.thirty.insitereadservice.flow.dto.response.ReferrerFlowResDto;
import com.thirty.insitereadservice.flow.dto.response.UrlFlowResDto;
import com.thirty.insitereadservice.flow.dto.request.EntryExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.EntryExitFlowResDto;

public interface FlowService {

    EntryExitFlowResDto getEntryExitFlow(EntryExitFlowReqDto exitFlowReqDto, int memberId);
    UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto,int memberId);
    ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto,int memberId);
    ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto,int memberId);
    BounceResDto getBounceCounts(BounceReqDto bounceReqDto,int memberId);
}
