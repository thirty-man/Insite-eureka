package com.thirty.insiterealtimereadservice.button.service;

import com.thirty.insiterealtimereadservice.button.dto.response.RealTimeCountResDto;
import com.thirty.insiterealtimereadservice.button.measurement.Button;
import java.util.List;

public interface ButtonService {

    RealTimeCountResDto readRealTimeCount(String serviceToken, String name);
}
