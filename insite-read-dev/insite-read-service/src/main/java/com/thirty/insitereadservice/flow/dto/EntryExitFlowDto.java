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
public class EntryExitFlowDto implements Comparable<EntryExitFlowDto>{

    private int id;

    private String exitPage;

    private int exitCount;

    private double exitRate;

    public static EntryExitFlowDto create(String exitPage, int exitCount, double exitRate){
        return EntryExitFlowDto.builder()
            .exitPage(exitPage)
            .exitCount(exitCount)
            .exitRate(exitRate)
            .build();
    }

    public EntryExitFlowDto addId(int id){
        this.id = id;
        return this;
    }


    @Override
    public int compareTo(@NotNull EntryExitFlowDto o) {
        return o.exitCount - this.getExitCount();
    }
}
