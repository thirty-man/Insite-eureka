package com.thirty.insitereadservice.flow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitFlowDto {

    private String exitPage;

    private int exitCount;

    private double exitRate;

    public static ExitFlowDto create(String exitPage, int exitCount, double exitRate){
        return ExitFlowDto.builder()
            .exitPage(exitPage)
            .exitCount(exitCount)
            .exitRate(exitRate)
            .build();
    }
}
