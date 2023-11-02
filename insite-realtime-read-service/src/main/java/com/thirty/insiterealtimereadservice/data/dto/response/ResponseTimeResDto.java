package com.thirty.insiterealtimereadservice.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTimeResDto {

    private double responseTime;

    public static ResponseTimeResDto create(double responseTime){
        return ResponseTimeResDto.builder()
            .responseTime(responseTime)
            .build();
    }
}
