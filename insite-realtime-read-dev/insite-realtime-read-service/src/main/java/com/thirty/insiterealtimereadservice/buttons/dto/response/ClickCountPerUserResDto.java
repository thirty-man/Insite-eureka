package com.thirty.insiterealtimereadservice.buttons.dto.response;

import com.thirty.insiterealtimereadservice.buttons.dto.CountPerUserDto;
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
public class ClickCountPerUserResDto {

    @Default
    private List<CountPerUserDto> countPerUserDtoList = new ArrayList<>();

    public static ClickCountPerUserResDto create(List<CountPerUserDto> countPerUserDtoList){
        return ClickCountPerUserResDto.builder()
            .countPerUserDtoList(countPerUserDtoList)
            .build();
    }
}
