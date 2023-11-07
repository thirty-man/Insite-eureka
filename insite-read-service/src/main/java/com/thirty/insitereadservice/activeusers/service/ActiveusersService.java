package com.thirty.insitereadservice.activeusers.service;

import com.thirty.insitereadservice.activeusers.dto.request.ActiveUsersPerTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUsersPerTimeResDto;
import com.thirty.insitereadservice.activeusers.dto.request.ActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.AverageActiveTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.OsActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUserResDto;
import com.thirty.insitereadservice.activeusers.dto.response.AverageActiveTimeResDto;
import com.thirty.insitereadservice.activeusers.dto.response.OsActiveUserResDto;
import java.text.ParseException;

public interface ActiveusersService {

    ActiveUsersPerTimeResDto getActiveUsersPerTime(ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto, int memberId);
    ActiveUserResDto getActiveUserCount(ActiveUserReqDto activeUserReqDto,int memberId);
    AverageActiveTimeResDto getAverageActiveTime(AverageActiveTimeReqDto averageActiveTimeReqDto,int memberId) throws ParseException;
    OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId);
}
