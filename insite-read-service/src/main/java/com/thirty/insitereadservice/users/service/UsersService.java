package com.thirty.insitereadservice.users.service;

import com.thirty.insitereadservice.activeusers.dto.request.ViewCountsPerActiveUserReqDto;
import com.thirty.insitereadservice.users.dto.request.*;
import com.thirty.insitereadservice.users.dto.response.*;
import com.thirty.insitereadservice.users.dto.UserCountDto;

public interface UsersService {

    AbnormalHistoryResDto getAbnormalHistory(AbnormalHistoryReqDto abnormalHistoryReqDto, int memberId);

    PageViewResDto getPageView(PageViewReqDto pageViewReqDto, int memberId);

    UserCountResDto getUserCount(UserCountReqDto userCountReqDto, int memberId);

    TotalUserCountResDto getTotalUserCount(TotalUserCountReqDto totalUserCountReqDto, int memberId);
    CookieIdUrlResDto getCookieIdUrlCount(ViewCountsPerUserReqDto viewCountsPerUserReqDto, int memberId);
}
