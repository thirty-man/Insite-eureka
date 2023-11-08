package com.thirty.insitereadservice.users.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime date;

    private String currentUrl;

    private String language;

    private int requestCnt;

    private String osId;

    public static AbnormalDto create(String cookieId, LocalDateTime date, String currentUrl, String language, int requestCnt, String osId){
        return AbnormalDto.builder()
            .cookieId(cookieId)
            .date(date)
            .currentUrl(currentUrl)
            .language(language)
            .requestCnt(requestCnt)
            .osId(osId)
            .build();
    }

    public AbnormalDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull AbnormalDto o) {
        return this.date.compareTo(o.date);
    }
}
