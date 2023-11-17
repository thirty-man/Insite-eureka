package com.thirty.insitememberservice.application.service;

import com.thirty.insitememberservice.application.dto.request.*;
import com.thirty.insitememberservice.application.dto.response.ApplicationCreateResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationTokenResDto;

public interface ApplicationService {
    ApplicationCreateResDto regist(ApplicationCreateReqDto applicationCreateReqDto, int memberId);

    void deleteApplication(ApplicationDeleteReqDto applicationDeleteReqDto,int memberId);

    void modifyApplication(ApplicationModifyReqDto applicationModifyReqDto, int memberId);

//    ApplicationTokenResDto getApplicationToken(ApplicationTokenReqDto applicationTokenReqDto, int memberId);

    ApplicationResDto getMyApplicationList(int memberId);

    void verifyIsValid(ApplicationVerifyReqDto applicationVerifyReqDto);



}
