package com.thirty.insiterealtimereadservice.buttons.service;

import com.thirty.insiterealtimereadservice.buttons.dto.response.CountPerUserResDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.CountResDto;

public interface ButtonService {

    CountResDto count(int memberId, String token);

    CountPerUserResDto countPerUser(int memberId, String token);
}
