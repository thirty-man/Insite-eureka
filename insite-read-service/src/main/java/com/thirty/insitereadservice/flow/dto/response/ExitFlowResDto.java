package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.ExitFlowDto;
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
public class ExitFlowResDto {

    @Default
    private List<ExitFlowDto> exitFlowDtoList = new ArrayList<>();

    public static ExitFlowResDto create(List<ExitFlowDto> exitFlowDtoList){
        return ExitFlowResDto.builder()
            .exitFlowDtoList(exitFlowDtoList)
            .build();
    }
}
