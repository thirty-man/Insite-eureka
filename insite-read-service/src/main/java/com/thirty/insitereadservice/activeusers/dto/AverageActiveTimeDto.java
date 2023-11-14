package com.thirty.insitereadservice.activeusers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageActiveTimeDto implements Comparable<AverageActiveTimeDto>{
    private int id;
    private String currentUrl;
    private double averageActiveTime;

    public static AverageActiveTimeDto create(String currentUrl,double averageActiveTime){
        return  AverageActiveTimeDto.builder()
                .currentUrl(currentUrl)
            .averageActiveTime(averageActiveTime)
            .build();
    }
    public AverageActiveTimeDto addId(int id){
        this.id=id;
        return this;
    }

    @Override
    public int compareTo(@NotNull AverageActiveTimeDto o) {
        return o.getAverageActiveTime()<=this.getAverageActiveTime()?-1:1;
    }
}
