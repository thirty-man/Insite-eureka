package com.thirty.insitereadservice.buttons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirstClickTimeResDto {

    private double firstClickTimeAvg;

    public static FirstClickTimeResDto create(double firstClickTimeAvg){
        return FirstClickTimeResDto.builder()
            .firstClickTimeAvg(firstClickTimeAvg)
            .build();
    }
}
