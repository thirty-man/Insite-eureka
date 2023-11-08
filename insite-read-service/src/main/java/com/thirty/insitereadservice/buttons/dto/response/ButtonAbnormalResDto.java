package com.thirty.insitereadservice.buttons.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonAbnormalResDto {

    private int id;

    private String cookieId;

    private String buttonName;

    private LocalDateTime currentDateTime;

    private String currentUrl;

    private int requestCnt;

    public static ButtonAbnormalResDto create(String cookieId, String buttonName, LocalDateTime currentDateTime, String currentUrl, int requestCnt){
        return ButtonAbnormalResDto.builder()
            .cookieId(cookieId)
            .buttonName(buttonName)
            .currentDateTime(currentDateTime)
            .currentUrl(currentUrl)
            .requestCnt(requestCnt)
            .build();
    }
}
