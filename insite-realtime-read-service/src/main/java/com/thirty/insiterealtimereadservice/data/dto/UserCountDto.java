package com.thirty.insiterealtimereadservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCountDto {

    private String currentPage;

    private int count;

    private double percentage;

    public static UserCountDto create(String currentPage, int count, double percentage){
        return UserCountDto.builder()
            .currentPage(currentPage)
            .count(count)
            .percentage(percentage)
            .build();
    }
}
