package com.thirty.insiterealtimereadservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCountDto implements Comparable<UserCountDto> {

    private int id;

    private String currentPage;

    private int userCount;

    private double percentage;

    private double responseTime;

    public static UserCountDto create(String currentPage, int userCount,double percentage, double responseTime){
        return UserCountDto.builder()
            .currentPage(currentPage)
            .userCount(userCount)
            .percentage(percentage)
            .responseTime(responseTime)
            .build();
    }

    public UserCountDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull UserCountDto o) {
        return o.getUserCount() - this.getUserCount();
    }
}
