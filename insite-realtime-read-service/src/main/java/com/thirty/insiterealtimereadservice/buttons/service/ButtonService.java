package com.thirty.insiterealtimereadservice.buttons.service;

import com.thirty.insiterealtimereadservice.buttons.dto.response.ButtonAbnormalResDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.ClickCountPerUserResDto;
import java.util.List;

public interface ButtonService {

    ClickCountPerUserResDto countPerUser(int memberId, String applicationToken);

    List<ButtonAbnormalResDto> getButtonAbnormal(int memberId, String applicationToken);
}
