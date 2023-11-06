package com.thirty.insitereadservice.buttons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickCountsPerActiveUserResDto {

    private double counts;

    public static ClickCountsPerActiveUserResDto create(double counts){
        return ClickCountsPerActiveUserResDto.builder()
            .counts(counts)
            .build();
    }
}
