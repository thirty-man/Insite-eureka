package com.thirty.insiterealtimereadservice.button.service;

import com.thirty.insiterealtimereadservice.button.dto.response.CountPerUserResDto;
import com.thirty.insiterealtimereadservice.button.dto.response.CountResDto;

public interface ButtonService {

    CountResDto count(int memberId, String token);

    CountPerUserResDto countPerUser(int memberId, String token);
}
