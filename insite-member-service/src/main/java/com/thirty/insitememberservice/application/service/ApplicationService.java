package com.thirty.insitememberservice.application.service;

import com.thirty.insitememberservice.application.dto.request.ApplicationCreateReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationDeleteReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationModifyReqDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationCreateResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationResDto;

public interface ApplicationService {
    ApplicationCreateResDto regist(ApplicationCreateReqDto applicationCreateReqDto, int memberId);

    void deleteApplication(ApplicationDeleteReqDto applicationDeleteReqDto,int memberId);

    void modifyApplication(ApplicationModifyReqDto applicationModifyReqDto, int memberId);

    ApplicationResDto getMyApplicationList(int memberId);





}
