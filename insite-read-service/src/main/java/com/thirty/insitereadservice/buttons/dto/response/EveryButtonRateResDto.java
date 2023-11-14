package com.thirty.insitereadservice.buttons.dto.response;

import com.thirty.insitereadservice.buttons.dto.ButtonRateDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EveryButtonRateResDto {

    private double totalAvg;

    @Default
    private List<ButtonRateDto> buttonDistDtoList = new ArrayList<>();

    public static EveryButtonRateResDto create(double totalAvg, List<ButtonRateDto> buttonDistDtoList){
        return EveryButtonRateResDto.builder()
            .totalAvg(totalAvg)
            .buttonDistDtoList(buttonDistDtoList)
            .build();
    }

}
