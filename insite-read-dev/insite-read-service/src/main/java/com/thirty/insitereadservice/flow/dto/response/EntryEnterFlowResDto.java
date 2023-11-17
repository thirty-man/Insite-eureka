package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.EntryEnterFlowDto;
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
public class EntryEnterFlowResDto {

    @Default
    private List<EntryEnterFlowDto> enterFlowDtoList = new ArrayList<>();

    public static EntryEnterFlowResDto create(List<EntryEnterFlowDto> enterFlowDtoList){
        return EntryEnterFlowResDto.builder()
            .enterFlowDtoList(enterFlowDtoList)
            .build();
    }
}
