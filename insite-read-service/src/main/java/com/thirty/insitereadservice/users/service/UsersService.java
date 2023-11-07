package com.thirty.insitereadservice.users.service;

import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.response.PageViewResDto;
import com.thirty.insitereadservice.users.dto.response.UserCountResDto;
import com.thirty.insitereadservice.users.dto.request.AbnormalHistoryReqDto;
import com.thirty.insitereadservice.users.dto.response.AbnormalHistoryResDto;

public interface UsersService {

    AbnormalHistoryResDto getAbnormalHistory(AbnormalHistoryReqDto abnormalHistoryReqDto, int memberId);

    PageViewResDto getPageView(PageViewReqDto pageViewReqDto,int memberId);

    UserCountResDto getUserCount(UserCountReqDto userCountReqDto,int memberId);
}
