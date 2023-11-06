package com.thirty.insitereadservice.cumulativedata.flow.dto.resDto;

import com.thirty.insitereadservice.cumulativedata.flow.dto.ReferrerFlowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferrerFlowResDto {
    private List<ReferrerFlowDto> referrerFlowDtos;

    public static ReferrerFlowResDto from(List<ReferrerFlowDto> referrerFlowDtoList){
        return ReferrerFlowResDto.builder().referrerFlowDtos(referrerFlowDtoList).build();
    }
}
