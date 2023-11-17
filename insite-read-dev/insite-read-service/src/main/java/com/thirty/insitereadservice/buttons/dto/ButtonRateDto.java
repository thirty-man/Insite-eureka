package com.thirty.insitereadservice.buttons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonRateDto {

    private int id;

    private String name;

    private int clickCounts;

    private double increaseDecreaseRate;

    public static ButtonRateDto create(String name, int clickCounts, double increaseDecreaseRate){
        return ButtonRateDto.builder()
            .name(name)
            .clickCounts(clickCounts)
            .increaseDecreaseRate(increaseDecreaseRate)
            .build();
    }

    public ButtonRateDto saveValues(int clickCounts, double increaseDecreaseRate){
        this.clickCounts = clickCounts;
        this.increaseDecreaseRate = increaseDecreaseRate;
        return this;
    }

    public ButtonRateDto addId(int id){
        this.id = id;
        return this;
    }
}
