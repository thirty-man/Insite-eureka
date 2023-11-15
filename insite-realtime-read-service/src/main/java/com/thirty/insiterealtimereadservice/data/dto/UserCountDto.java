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

    private int viewCount;

    private int userCount;

    private double responseTime;

    public static UserCountDto create(String currentPage, int viewCount, int userCount, double responseTime){
        return UserCountDto.builder()
            .currentPage(currentPage)
            .viewCount(viewCount)
            .userCount(userCount)
            .responseTime(responseTime)
            .build();
    }

    public UserCountDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull UserCountDto o) {
        return o.getViewCount() - this.getViewCount();
    }
}
