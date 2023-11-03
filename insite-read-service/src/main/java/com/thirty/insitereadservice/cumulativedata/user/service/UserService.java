package com.thirty.insitereadservice.cumulativedata.user.service;


import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.PageViewReqDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.UserCountReqDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.PageViewResDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.UserCountResDto;

public interface UserService {
    PageViewResDto getPageView(PageViewReqDto pageViewReqDto,int memberId);

    UserCountResDto getUserCount(UserCountReqDto userCountReqDto,int memberId);



}
