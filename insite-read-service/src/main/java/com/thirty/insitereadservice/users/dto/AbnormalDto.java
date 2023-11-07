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

    private LocalDateTime date;

    private String url;

    private int abnormalCount;

    public static AbnormalDto create(LocalDateTime date, String url, int abnormalCount){
        return AbnormalDto.builder()
            .date(date)
            .url(url)
            .abnormalCount(abnormalCount)
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
