package com.thirty.insitereadservice.feignclient;


import javax.validation.Valid;

import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "insite-member-service")
public interface MemberServiceClient {
    @PostMapping(value = "/api/v1/members/valid")
    void validationMemberAndApplication(
            @Valid @RequestBody MemberValidReqDto memberValidReqDto
    );
}


