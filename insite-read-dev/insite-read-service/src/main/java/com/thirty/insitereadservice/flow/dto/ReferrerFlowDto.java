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
public class ReferrerFlowDto implements Comparable<ReferrerFlowDto>{
    private int id;

    private String referrer;

    private int count;

    public static ReferrerFlowDto create(String referrer, int count){
        return ReferrerFlowDto.builder()
            .referrer(referrer)
            .count(count)
            .build();
    }

    public ReferrerFlowDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull ReferrerFlowDto o) {
        return o.count - this.getCount();
    }
}
