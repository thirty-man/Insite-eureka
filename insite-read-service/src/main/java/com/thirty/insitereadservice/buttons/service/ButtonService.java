package com.thirty.insitereadservice.buttons.service;

import com.thirty.insitereadservice.buttons.dto.request.ButtonAbnormalReqDto;
import com.thirty.insitereadservice.buttons.dto.request.EveryButtonRateReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ButtonLogsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ButtonAbnormalResDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsResDto;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.EveryButtonRateResDto;
import com.thirty.insitereadservice.buttons.dto.response.ButtonLogsResDto;
import java.util.List;

public interface ButtonService {

    ClickCountsResDto getClickCounts(ClickCountsReqDto clickCountsReqDto, int memberId);

    ButtonLogsResDto getButtonLogs(ButtonLogsReqDto buttonLogsReqDto, int memberId);

    List<ButtonAbnormalResDto> getButtonAbnormal(ButtonAbnormalReqDto buttonAbnormalReqDto, int memberId);

    EveryButtonRateResDto getEveryButtonRate(EveryButtonRateReqDto everyButtonDistReqDto, int memberId);
}
