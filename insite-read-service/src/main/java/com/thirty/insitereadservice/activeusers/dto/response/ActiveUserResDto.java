package com.thirty.insitereadservice.activeusers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUserResDto {
    private int activeUserCount;

    public static ActiveUserResDto create (int activeUserCount){
        return ActiveUserResDto.builder()
            .activeUserCount(activeUserCount)
            .build();
    }
}
