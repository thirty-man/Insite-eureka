package com.thirty.insitereadservice.feignclient;

import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "insite-member-service")
public interface MemberServiceClient {

    @PostMapping(value = "api/v1/members/valid")
    void validationMemberAndApplication(
        @Valid @RequestBody MemberValidReqDto memberValidReqDto
    );
}


>>>>>>> de2480eaabdf04b33847e7b3c0cd93914e9ee90b
