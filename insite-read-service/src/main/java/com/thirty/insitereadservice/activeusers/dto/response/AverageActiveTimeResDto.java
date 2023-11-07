package com.thirty.insitereadservice.activeusers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageActiveTimeResDto {
    private double averageActiveTime;

    public static AverageActiveTimeResDto create(double averageActiveTime){
        return  AverageActiveTimeResDto.builder()
            .averageActiveTime(averageActiveTime)
            .build();
    }
}
