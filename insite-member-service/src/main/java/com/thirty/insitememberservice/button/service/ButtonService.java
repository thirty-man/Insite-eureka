package com.thirty.insitememberservice.button.service;

import com.thirty.insitememberservice.button.dto.response.ButtonCreateResDto;
import com.thirty.insitememberservice.button.dto.request.ButtonCreateReqDto;
import com.thirty.insitememberservice.button.dto.request.ButtonDeleteReqDto;
import com.thirty.insitememberservice.button.dto.request.ButtonModifyReqDto;
import com.thirty.insitememberservice.button.dto.response.ButtonListResDto;

public interface ButtonService {

    ButtonCreateResDto create(int memberId, ButtonCreateReqDto buttonCreateReqDto);

    void delete(int memberId, int buttonId, ButtonDeleteReqDto buttonDeleteReqDto);

    void modify(int memberId, int buttonId, ButtonModifyReqDto buttonModifyReqDto);

    ButtonListResDto getMyButtonList(int memberId, int applicationId, int page);
}
