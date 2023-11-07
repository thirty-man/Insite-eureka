package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.request.BounceReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BounceResDto {
    private int count;

    public static BounceResDto create(int count){
        return BounceResDto.builder()
            .count(count)
            .build();
    }
}
