package com.thirty.insiterealtimereadservice.button.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealTimeCountResDto {
    private int count;

    public static RealTimeCountResDto create(int count){
        return RealTimeCountResDto.builder()
            .count(count)
            .build();
    }
}
