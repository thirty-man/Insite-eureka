package com.thirty.insiterealtimereadservice.buttons.dto.response;

import com.thirty.insiterealtimereadservice.buttons.dto.CountDto;
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
public class CountResDto {

    @Default
    private List<CountDto> countDtoList = new ArrayList<>();

    public static CountResDto create(List<CountDto> count){
        return CountResDto.builder()
            .countDtoList(count)
            .build();
    }
}
