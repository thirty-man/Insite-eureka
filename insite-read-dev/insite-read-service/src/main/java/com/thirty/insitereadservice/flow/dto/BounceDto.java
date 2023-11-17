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
public class BounceDto implements Comparable<BounceDto>{
    private int id;
    private String currentUrl;
    private int count;
    private double ratio;
    public static BounceDto create(String currentUrl,int count){
        return BounceDto.builder()
                .currentUrl(currentUrl)
                .count(count)
                .build();
    }
    public void addCount(){
        ++this.count;
    }
    public BounceDto addSize(int size){
        this.ratio=(double)count/(double)size;
        return this;
    }
    public BounceDto addId(int id){
        this.id=id;
        return this;
    }


    @Override
    public int compareTo(@NotNull BounceDto o) {
        return o.count-this.count;
    }
}
