package com.thirty.insitememberservice.button.dto.response;

import com.thirty.insitememberservice.button.dto.ButtonDto;
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
public class ButtonListResDto {

    @Default
    private List<ButtonDto> buttonDtoList = new ArrayList<>();

    private int totalPages;

    private int currentPage;

    private Boolean hasNext;

    public static ButtonListResDto create(List<ButtonDto> buttonDtoList, int totalPages, int currentPage, Boolean hasNext){
        return ButtonListResDto.builder()
            .buttonDtoList(buttonDtoList)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .hasNext(hasNext)
            .build();
    }
}
