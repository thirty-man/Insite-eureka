package com.thirty.insiterealtimereadservice.buttons.service;

import com.thirty.insiterealtimereadservice.buttons.dto.response.CountPerUserResDto;

public interface ButtonService {

    CountPerUserResDto countPerUser(int memberId, String token);
}
