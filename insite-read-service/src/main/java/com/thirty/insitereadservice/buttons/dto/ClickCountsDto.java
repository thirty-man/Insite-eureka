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
public class ClickCountsDto {

    private int counts;

    private String date;

    public static ClickCountsDto create(String date, int counts){
        return ClickCountsDto.builder()
            .date(date)
            .counts(counts)
            .build();

    }
}
