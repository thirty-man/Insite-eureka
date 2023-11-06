package com.thirty.insitereadservice.cumulativedata.activeuser.service;

import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.ActiveUserReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.AverageActiveTimeReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.OsActiveUserReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.ActiveUserResDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.AverageActiveTimeResDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.OsActiveUserResDto;

import java.text.ParseException;

public interface ActiveUserService {
    ActiveUserResDto getActiveUserCount(ActiveUserReqDto activeUserReqDto,int memberId);
    AverageActiveTimeResDto getAverageActiveTime(AverageActiveTimeReqDto averageActiveTimeReqDto,int memberId) throws ParseException;
    OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId);
}
