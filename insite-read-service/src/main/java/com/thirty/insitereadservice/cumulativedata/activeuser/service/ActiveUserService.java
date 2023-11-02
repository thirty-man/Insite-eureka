package com.thirty.insitereadservice.cumulativedata.activeuser.service;

import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.ActiveUserReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.AverageActiveTimeReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.OsActiveUserReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.ActiveUserResDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.AverageActiveTimeResDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.OsActiveUserResDto;

public interface ActiveUserService {
    ActiveUserResDto getActiveUserCount(ActiveUserReqDto activeUserReqDto);
    AverageActiveTimeResDto getAverageActiveTime(AverageActiveTimeReqDto averageActiveTimeReqDto);
    OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto);
}
