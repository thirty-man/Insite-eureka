package com.thirty.insitereadservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewCountsPerUserDto implements Comparable<ViewCountsPerUserDto>{
    private String currentUrl;
    private int count;
    private double ratio;

    public static ViewCountsPerUserDto create(String currentUrl,int count){
        return ViewCountsPerUserDto.builder().currentUrl(currentUrl).count(count).build();
    }
    public void setRatio(int size){
        this.ratio=(double)count/(double)size;
    }

    @Override
    public int compareTo(@NotNull ViewCountsPerUserDto o) {
        return o.getCount()-this.getCount();
    }
}
