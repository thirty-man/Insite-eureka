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
public class UrlFlowDto implements Comparable<UrlFlowDto>{

    private int id;

    private String beforeUrl;

    private int count;

    public static UrlFlowDto create(String beforeUrl, int count){
        return UrlFlowDto.builder()
            .beforeUrl(beforeUrl)
            .count(count)
            .build();
    }

    public UrlFlowDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull UrlFlowDto o) {
        return o.count - this.getCount();
    }
}
