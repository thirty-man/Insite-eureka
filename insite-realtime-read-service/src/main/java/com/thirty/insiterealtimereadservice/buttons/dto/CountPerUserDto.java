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

    private Double countPerUser;

    public static CountPerUserDto create(String name, Double countPerUser){
        return CountPerUserDto.builder()
            .name(name)
            .countPerUser(countPerUser)
            .build();
    }
}
