package com.thirty.insitereadservice.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResDto {

    private int evertActiveUserCount;

    private int everyInflowUserCount;

    private double viewCountPerActiveUser;

    private int everyAbnormalCount;

    public static CommonResDto create(int evertActiveUserCount, int everyInflowUserCount, double viewCountPerActiveUser, int everyAbnormalCount){
        return CommonResDto.builder()
            .evertActiveUserCount(evertActiveUserCount)
            .everyInflowUserCount(everyInflowUserCount)
            .viewCountPerActiveUser(viewCountPerActiveUser)
            .everyAbnormalCount(everyAbnormalCount)
            .build();
    }
}
