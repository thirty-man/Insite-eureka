package com.thirty.insitereadservice.buttons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitPercentageResDto {

    private double exitRate;

    public static ExitPercentageResDto create(double exitRate){
        return ExitPercentageResDto.builder()
            .exitRate(exitRate)
            .build();
    }
}
