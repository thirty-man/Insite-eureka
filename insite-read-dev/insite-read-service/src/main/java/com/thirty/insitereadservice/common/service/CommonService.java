package com.thirty.insitereadservice.common.service;

import com.thirty.insitereadservice.common.dto.request.CommonReqDto;
import com.thirty.insitereadservice.common.dto.response.CommonResDto;

public interface CommonService {

    CommonResDto getCommonInfo(CommonReqDto commonReqDto, int memberId);
}
