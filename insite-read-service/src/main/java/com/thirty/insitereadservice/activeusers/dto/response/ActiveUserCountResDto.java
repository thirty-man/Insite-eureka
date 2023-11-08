package com.thirty.insitereadservice.activeusers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUserCountResDto {
    private int activeUserCount;

    public static ActiveUserCountResDto create(int count){
        return ActiveUserCountResDto.builder().activeUserCount(count).build();
    }

}
