package com.thirty.insitewriteservice.feignclient.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationVerifyReqDto {
    @NotNull(message = "applicationToken 기입 필요")
    private String applicationToken;
    @NotNull(message = "applicationUrl 기입 필요")
    private String applicationUrl;

    public static ApplicationVerifyReqDto create(String token, String url) {
        return ApplicationVerifyReqDto.builder()
                .applicationToken(token)
                .applicationUrl(url)
                .build();
    }
}
