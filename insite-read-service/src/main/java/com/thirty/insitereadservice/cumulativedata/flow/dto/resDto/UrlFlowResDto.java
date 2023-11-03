package com.thirty.insitereadservice.cumulativedata.flow.dto.resDto;

import com.thirty.insitereadservice.cumulativedata.flow.dto.UrlFlowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlFlowResDto {
    private List<UrlFlowDto> urlFlowDtoList;

    public static UrlFlowResDto from(List<UrlFlowDto> urlFlowDtoList){
        return UrlFlowResDto.builder().urlFlowDtoList(urlFlowDtoList).build();
    }

}
