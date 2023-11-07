package com.thirty.insitereadservice.flow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryExitFlowDto {

    private String exitPage;

    private int exitCount;

    private double exitRate;

    public static EntryExitFlowDto create(String exitPage, int exitCount, double exitRate){
        return EntryExitFlowDto.builder()
            .exitPage(exitPage)
            .exitCount(exitCount)
            .exitRate(exitRate)
            .build();
    }
}
