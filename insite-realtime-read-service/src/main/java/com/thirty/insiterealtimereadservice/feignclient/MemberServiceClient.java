package com.thirty.insiterealtimereadservice.feignclient;

import com.thirty.insiterealtimereadservice.feignclient.dto.request.MemberValidReqDto;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "insite-member-service")
public interface MemberServiceClient {

    @PostMapping(value = "api/v1/members/valid")
    void validationMemberAndApplication(
        @Valid @RequestBody MemberValidReqDto memberValidReqDto
    );
}
