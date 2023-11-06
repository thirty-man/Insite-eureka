package com.thirty.insitereadservice.users.service;

import com.thirty.insitereadservice.users.dto.request.AbnormalHistoryReqDto;
import com.thirty.insitereadservice.users.dto.response.AbnormalHistoryResDto;

public interface UsersService {

    AbnormalHistoryResDto getAbnormalHistory(AbnormalHistoryReqDto abnormalHistoryReqDto, int memberId);
}
