package com.thirty.insitewriteservice.feignclient;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thirty.insitewriteservice.feignclient.dto.request.ApplicationVerifyReqDto;

@FeignClient(name = "insite-member-service")
public interface ApplicationServiceClient {

	@PostMapping(value = "/api/v1/application/verify")
	void validationApplication(@Valid @RequestBody ApplicationVerifyReqDto applicationVerifyReqDto);
}

