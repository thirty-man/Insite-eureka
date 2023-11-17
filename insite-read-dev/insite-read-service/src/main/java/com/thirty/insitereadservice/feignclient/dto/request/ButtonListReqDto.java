package com.thirty.insitereadservice.feignclient.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonListReqDto {
    @NotNull(message = "앱 토큰을 기입해주세요.")
    private String applicationToken;

    public static ButtonListReqDto create(String applicationToken){
        return ButtonListReqDto.builder()
            .applicationToken(applicationToken)
            .build();
    }
}
