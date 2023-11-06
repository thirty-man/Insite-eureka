package com.thirty.insiterealtimereadservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalDto {

    private String cookieId;

    private String time;

    private String currentUrl;

    private String language;

    private String osId;

    public static AbnormalDto create(String cookieId, String time, String currentUrl, String language, String osId){
        return AbnormalDto.builder()
            .cookieId(cookieId)
            .time(time)
            .currentUrl(currentUrl)
            .language(language)
            .osId(osId)
            .build();
    }
}
