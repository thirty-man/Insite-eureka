package com.thirty.insiterealtimereadservice.buttons.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClickCountPerUserReqDto {
    @NotNull(message = "토큰을 기입해주세요")
    private String token;
}
