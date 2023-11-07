package com.thirty.insitewriteservice.feignclient;

import com.thirty.insitewriteservice.feignclient.dto.request.ApplicationVerifyReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

@FeignClient(name = "insite-member-service")
public interface ApplicationServiceClient {

    @PostMapping(value = "api/v1/application/verify")
    static void validationApplication(@Valid @RequestBody ApplicationVerifyReqDto applicationVerifyReqDto) {}
}

