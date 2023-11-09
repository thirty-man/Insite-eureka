package com.thirty.insitereadservice.feignclient;


import com.thirty.insitereadservice.feignclient.dto.request.ButtonListReqDto;
import com.thirty.insitereadservice.feignclient.dto.response.ButtonListResDto;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//eureka 안 키면 url로 로컬 서버를 등록해줘야 통신 가능
//eureka 키면 name으로 통신 가능해서 지워줄 것
@FeignClient(name = "insite-member-service",url = "http://localhost:8081")
public interface MemberServiceClient {

    @PostMapping(value = "/api/v1/members/valid")
    void validationMemberAndApplication(
            @Valid @RequestBody MemberValidReqDto memberValidReqDto
    );

    @PostMapping(value = "/api/v1/buttons/list/{memberId}")
    ButtonListResDto getMyButtonList(
        @Valid @RequestBody ButtonListReqDto buttonListReqDto,
        @Valid @PathVariable int memberId
    );
}


