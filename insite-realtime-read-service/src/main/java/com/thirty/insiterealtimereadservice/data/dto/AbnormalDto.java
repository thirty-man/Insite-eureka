package com.thirty.insiterealtimereadservice.data.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalDto implements Comparable<AbnormalDto>{

    private int id;

    private String cookieId;

    private LocalDateTime time;

    private String currentUrl;

    private String language;

    private String osId;

    public static AbnormalDto create(String cookieId, LocalDateTime time, String currentUrl, String language, String osId){
        return AbnormalDto.builder()
            .cookieId(cookieId)
            .time(time)
            .currentUrl(currentUrl)
            .language(language)
            .osId(osId)
            .build();
    }

    public AbnormalDto addId(int id){
        this.id = id;
        return this;
    }


    @Override
    public int compareTo(@NotNull AbnormalDto o) {
        return o.getTime().compareTo(this.getTime());
    }
}
