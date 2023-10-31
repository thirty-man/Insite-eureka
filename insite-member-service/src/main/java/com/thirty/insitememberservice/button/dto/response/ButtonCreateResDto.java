package com.thirty.insitememberservice.button.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonCreateResDto {
    private int id;

    public static ButtonCreateResDto create(int id){
        return ButtonCreateResDto.builder()
            .id(id)
            .build();
    }
}
