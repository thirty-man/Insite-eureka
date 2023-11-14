package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.EntryExitFlowDto;
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
public class EntryExitFlowResDto {

    @Default
    private List<EntryExitFlowDto> exitFlowDtoList = new ArrayList<>();

    public static EntryExitFlowResDto create(List<EntryExitFlowDto> exitFlowDtoList){
        return EntryExitFlowResDto.builder()
            .exitFlowDtoList(exitFlowDtoList)
            .build();
    }
}
