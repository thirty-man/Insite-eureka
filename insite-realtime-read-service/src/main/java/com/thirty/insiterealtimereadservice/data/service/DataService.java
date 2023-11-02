package com.thirty.insiterealtimereadservice.data.service;

import com.thirty.insiterealtimereadservice.data.dto.response.AbnormalResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.ReferrerResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.ResponseTimeResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.UserCountResDto;

public interface DataService {

    ResponseTimeResDto getResponseTime(int memberId, String token);

    ReferrerResDto getReferrer(int memberId, String token);

    UserCountResDto getUserCount(int memberId, String token);

    AbnormalResDto getAbnormal(int memberId, String token);
}
