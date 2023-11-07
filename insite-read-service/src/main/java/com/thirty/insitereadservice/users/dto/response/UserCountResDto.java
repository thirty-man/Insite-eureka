package com.thirty.insitereadservice.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCountResDto {

    private int userCount;

    public static UserCountResDto create(int userCount){
        return UserCountResDto.builder()
            .userCount(userCount)
            .build();
    }
}
