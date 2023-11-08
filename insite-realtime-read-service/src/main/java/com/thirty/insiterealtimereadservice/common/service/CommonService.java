package com.thirty.insiterealtimereadservice.common.service;


import com.thirty.insiterealtimereadservice.common.dto.request.CommonReqDto;
import com.thirty.insiterealtimereadservice.common.dto.response.CommonResDto;

public interface CommonService {

    CommonResDto getCommonInfo(CommonReqDto commonReqDto, int memberId);
}
