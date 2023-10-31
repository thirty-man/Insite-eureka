package com.thirty.insitememberservice.button.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonDto {

    private int id;

    private String name;

    public static ButtonDto create(int id, String name){
        return ButtonDto.builder()
            .id(id)
            .name(name)
            .build();
    }
}
