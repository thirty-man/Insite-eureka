package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.UrlFlowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUrlFlowResDto {
    private List<UrlFlowDto> urlFlowDtoList;

    public static CurrentUrlFlowResDto from(List<UrlFlowDto> urlFlowDtoList){
        return CurrentUrlFlowResDto.builder().urlFlowDtoList(urlFlowDtoList).build();
    }

}
