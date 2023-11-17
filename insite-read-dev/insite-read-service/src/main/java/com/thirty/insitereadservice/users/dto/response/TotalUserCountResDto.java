package com.thirty.insitereadservice.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalUserCountResDto {
    private int total;

    public static TotalUserCountResDto create(int total){
        return TotalUserCountResDto.builder().total(total).build();
    }
}
