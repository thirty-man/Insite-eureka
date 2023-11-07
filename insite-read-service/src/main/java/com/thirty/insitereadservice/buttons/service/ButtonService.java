package com.thirty.insitereadservice.buttons.service;

import com.thirty.insitereadservice.buttons.dto.request.ClickCountsPerActiveUserReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ExitPercentageReqDto;
import com.thirty.insitereadservice.buttons.dto.request.FirstClickTimeReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsPerActiveUserResDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsResDto;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ExitPercentageResDto;
import com.thirty.insitereadservice.buttons.dto.response.FirstClickTimeResDto;

public interface ButtonService {

    ClickCountsResDto getClickCounts(ClickCountsReqDto clickCountsReqDto, int memberId);

    ClickCountsPerActiveUserResDto getClickCountsPerActiveUser(ClickCountsPerActiveUserReqDto clickCountsPerActiveUserReqDto, int memberId);

    ExitPercentageResDto getExitPercentage(ExitPercentageReqDto exitCountsReqDto, int memberId);

//    FirstClickTimeResDto getFirstClickTimeAvg(FirstClickTimeReqDto firstClickTimeReqDto, int memberId);
}
