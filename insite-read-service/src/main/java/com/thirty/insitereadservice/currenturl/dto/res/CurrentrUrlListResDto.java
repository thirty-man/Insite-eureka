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
public class CurrentrUrlListResDto {
    private List<CurrentUrlDto> currentUrlDtoList;
    public static CurrentrUrlListResDto create(List<CurrentUrlDto>currentUrlDtoList){
        return CurrentrUrlListResDto.builder().currentUrlDtoList(currentUrlDtoList).build();
    }
}
