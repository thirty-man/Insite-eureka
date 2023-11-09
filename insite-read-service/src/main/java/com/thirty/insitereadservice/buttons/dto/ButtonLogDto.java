package com.thirty.insitereadservice.buttons.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonLogDto {

    private int id;

    private String currentUrl;

    private LocalDateTime clickDateTime;

    private String cookieId;

    private boolean isAbnormal;

    public static ButtonLogDto create(String currentUrl, LocalDateTime clickDateTime, String cookieId, boolean isAbnormal){
        return ButtonLogDto.builder()
            .currentUrl(currentUrl)
            .clickDateTime(clickDateTime)
            .cookieId(cookieId)
            .isAbnormal(isAbnormal)
            .build();
    }

    public ButtonLogDto addId(int id){
        this.id = id;
        return this;
    }
}
