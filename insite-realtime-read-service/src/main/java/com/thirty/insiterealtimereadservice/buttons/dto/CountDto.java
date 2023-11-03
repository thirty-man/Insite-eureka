package com.thirty.insiterealtimereadservice.buttons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountDto {

    private String name;

    private int count;

    public static CountDto create(String name, int count){
        return CountDto.builder()
            .name(name)
            .count(count)
            .build();
    }
}
