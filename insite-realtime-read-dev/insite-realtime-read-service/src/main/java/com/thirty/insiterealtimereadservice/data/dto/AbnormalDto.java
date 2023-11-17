package com.thirty.insiterealtimereadservice.data.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalDto{

    private int id;

    private String cookieId;

    private LocalDateTime time;

    private String currentUrl;

    private String language;

    private int requestCnt;

    private String osId;

    public static AbnormalDto create(String cookieId, LocalDateTime time, String currentUrl, String language, int requestCnt,String osId){
        return AbnormalDto.builder()
            .cookieId(cookieId)
            .time(time)
            .currentUrl(currentUrl)
            .language(language)
            .osId(osId)
            .requestCnt(requestCnt)
            .build();
    }

    public AbnormalDto addId(int id){
        this.id = id;
        return this;
    }
}
