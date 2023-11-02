package com.thirty.insiterealtimereadservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferrerDto {

    private String beforeUrl;

    private int count;

    private double percentage;

    public static ReferrerDto create(String beforeUrl, int count, double percentage){
        return ReferrerDto.builder()
            .beforeUrl(beforeUrl)
            .count(count)
            .percentage(percentage)
            .build();
    }
}
