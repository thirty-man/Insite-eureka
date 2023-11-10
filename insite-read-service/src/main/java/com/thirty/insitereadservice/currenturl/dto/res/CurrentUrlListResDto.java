package com.thirty.insitereadservice.currenturl.dto.res;

import com.thirty.insitereadservice.currenturl.dto.CurrentUrlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUrlListResDto {

    private List<CurrentUrlDto> currentUrlDtoList;

    public static CurrentUrlListResDto create(List<CurrentUrlDto>currentUrlDtoList){
        return CurrentUrlListResDto.builder()
            .currentUrlDtoList(currentUrlDtoList)
            .build();
    }
}
