package com.thirty.insitereadservice.flow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryEnterFlowDto implements Comparable<EntryEnterFlowDto>{
    private int id;

    private String enterPage;

    private int enterCount;

    private double enterRate;

    public static EntryEnterFlowDto create(String enterPage, int enterCount, double enterRate){
        return EntryEnterFlowDto.builder()
            .enterPage(enterPage)
            .enterCount(enterCount)
            .enterRate(enterRate)
            .build();
    }

    public EntryEnterFlowDto addId(int id){
        this.id = id;
        return this;
    }


    @Override
    public int compareTo(@NotNull EntryEnterFlowDto o) {
        return o.enterCount - this.getEnterCount();
    }
}
