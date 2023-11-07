package com.thirty.insitewriteservice.feignclient;

import com.thirty.insitewriteservice.feignclient.dto.request.ApplicationVerifyReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

@FeignClient(name = "applicationServiceClient", url = "http://localhost:8081")
public interface ApplicationServiceClient {

    @PostMapping(value = "/api/v1/application/verify")
    void validationApplication(@Valid @RequestBody ApplicationVerifyReqDto applicationVerifyReqDto);
}

