package com.thirty.insiterealtimereadservice.data.dto;

import com.thirty.insiterealtimereadservice.data.dto.response.UserCountResDto;
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

    private int count;

    private double percentage;

    private int responseTime;

    public static UserCountDto create(String currentPage, int count, double percentage, int responseTime){
        return UserCountDto.builder()
            .currentPage(currentPage)
            .count(count)
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
        return o.getCount() - this.getCount();
    }
}
