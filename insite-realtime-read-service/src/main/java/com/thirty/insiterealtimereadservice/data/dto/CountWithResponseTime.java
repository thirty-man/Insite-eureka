package com.thirty.insiterealtimereadservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountWithResponseTime {

    private int viewCount;

    private int userCount;

    private double responseTime;

    public static CountWithResponseTime create(int viewCount, int userCount, double responseTime){
        return CountWithResponseTime.builder()
            .viewCount(viewCount)
            .userCount(userCount)
            .responseTime(responseTime)
            .build();
    }
}
