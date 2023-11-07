package com.thirty.insiterealtimereadservice.buttons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPerUserDto {

    private String name;

    private int count;

    private double countPerUser;

    public static CountPerUserDto create(String name, int count, double countPerUser){
        return CountPerUserDto.builder()
            .name(name)
            .count(count)
            .countPerUser(countPerUser)
            .build();
    }
}
