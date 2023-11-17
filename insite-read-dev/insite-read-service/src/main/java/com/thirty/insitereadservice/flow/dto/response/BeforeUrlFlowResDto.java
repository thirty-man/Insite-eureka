package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.BeforeUrlFlowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeforeUrlFlowResDto {

    private List<BeforeUrlFlowDto> beforeUrlFlowDtoList;

    public static BeforeUrlFlowResDto create(List<BeforeUrlFlowDto> beforeUrlFlowDtoList){
        return BeforeUrlFlowResDto.builder().beforeUrlFlowDtoList(beforeUrlFlowDtoList).build();
    }
}
