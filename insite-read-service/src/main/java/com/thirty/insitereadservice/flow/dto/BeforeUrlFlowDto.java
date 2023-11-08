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
public class BeforeUrlFlowDto implements Comparable<BeforeUrlFlowDto>{
    private int id;
    private String beforeUrl;
    private int count;

    public static BeforeUrlFlowDto create(String beforeUrl,int count){
        return BeforeUrlFlowDto.builder().beforeUrl(beforeUrl).count(count).build();
    }
    public BeforeUrlFlowDto addId(int id){
        this.id=id;
        return this;
    }
    @Override
    public int compareTo(@NotNull BeforeUrlFlowDto o) {
        return o.count-this.getCount();
    }
}
