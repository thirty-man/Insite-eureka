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

    private int userCount;

    private double responseTime;

    public static CountWithResponseTime create(int userCount, double responseTime){
        return CountWithResponseTime.builder()
            .userCount(userCount)
            .responseTime(responseTime)
            .build();
    }

    public CountWithResponseTime addUserCount(){
        this.userCount++;
        return this;
    }

    public CountWithResponseTime addResponseTime(double responseTime){
        this.responseTime += responseTime;
        return this;
    }
}
