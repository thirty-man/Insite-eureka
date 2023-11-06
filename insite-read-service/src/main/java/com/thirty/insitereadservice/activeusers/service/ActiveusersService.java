package com.thirty.insitereadservice.activeusers.service;

import com.thirty.insitereadservice.activeusers.dto.request.ActiveUsersPerTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUsersPerTimeResDto;

public interface ActiveusersService {

    ActiveUsersPerTimeResDto getActiveUsersPerTime(ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto, int memberId);
}
