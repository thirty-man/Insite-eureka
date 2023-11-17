package com.thirty.insitereadservice.activeusers.service;

import com.thirty.insitereadservice.activeusers.dto.request.*;
import com.thirty.insitereadservice.activeusers.dto.response.*;

import java.text.ParseException;

public interface ActiveusersService {

    ActiveUsersPerTimeResDto getActiveUsersPerTime(ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto, int memberId);
    ActiveUserResDto getActiveUser(ActiveUserReqDto activeUserReqDto, int memberId);
    AverageActiveTimeResDto getAverageActiveTime(AverageActiveTimeReqDto averageActiveTimeReqDto, int memberId) throws ParseException;
    OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId);
    ActiveUserCountResDto getActiveUserCount(ActiveUserCountReqDto activeUserCountReqDto, int memberId);
    ViewCountsPerActiveUserResDto getViewCounts(ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto,int memberId);
    ActiveUserPerUserResDto getActiveUserPerUser(ActiveUserPerUserReqDto activeUserPerUserReqDto,int memberId);
}
