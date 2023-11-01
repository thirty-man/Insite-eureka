package com.thirty.insitememberservice.button.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ButtonCreateReqDto {
    @NotNull(message = "앱 아이디를 입력해주세요")
    private int applicationId;

    @NotNull(message = "버튼 이름을 입력해주세요")
    private String name;

}
