package com.thirty.insitereadservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalDto {

    private String date;

    private String url;

    private int abnormalCount;

    public static AbnormalDto create(String date, String url, int abnormalCount){
        return AbnormalDto.builder()
            .date(date)
            .url(url)
            .abnormalCount(abnormalCount)
            .build();
    }
}
