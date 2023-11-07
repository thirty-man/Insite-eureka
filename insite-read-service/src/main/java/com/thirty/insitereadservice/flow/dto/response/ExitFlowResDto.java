package com.thirty.insitereadservice.flow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitFlowResDto {
    private int exitCount;

    public static ExitFlowResDto create(int exitCount){
        return ExitFlowResDto.builder()
            .exitCount(exitCount)
            .build();
    }
}
