package com.thirty.insitememberservice.button.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ButtonDeleteReqDto {

    @NotNull(message = "앱 아이디를 기입해주세요")
    private int applicationId;
}
