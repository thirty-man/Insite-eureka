package com.thirty.insiterealtimereadservice.common.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonReqDto {
    @NotNull(message = "앱 토큰을 기입해주세요.")
    private String applicationToken;
}
