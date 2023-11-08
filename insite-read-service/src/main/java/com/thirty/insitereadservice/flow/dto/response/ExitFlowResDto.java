package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.ExitFlowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitFlowResDto {
    List<ExitFlowDto> exitFlowDtoList;

    public static ExitFlowResDto create(List<ExitFlowDto>exitFlowDtoList){
        return ExitFlowResDto.builder().exitFlowDtoList(exitFlowDtoList).build();
    }
}
