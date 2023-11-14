package com.thirty.insitereadservice.users.dto.response;

import com.thirty.insitereadservice.users.dto.PageViewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageViewResDto {
    private List<PageViewDto> pageViewDtoList;
    public static PageViewResDto create(List<PageViewDto> pageViewDtoList){
        return PageViewResDto.builder().pageViewDtoList(pageViewDtoList).build();
    }

}
