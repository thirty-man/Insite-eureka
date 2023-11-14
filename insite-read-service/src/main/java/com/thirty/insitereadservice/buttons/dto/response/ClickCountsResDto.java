package com.thirty.insitereadservice.buttons.dto.response;

import com.thirty.insitereadservice.buttons.dto.ClickCountsDto;
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
public class ClickCountsResDto {

    @Default
    private List<ClickCountsDto> clickCountsDtoList = new ArrayList<>();

    public static ClickCountsResDto create(List<ClickCountsDto> clickCountsDtoList){
        return ClickCountsResDto.builder()
            .clickCountsDtoList(clickCountsDtoList)
            .build();
    }
}
