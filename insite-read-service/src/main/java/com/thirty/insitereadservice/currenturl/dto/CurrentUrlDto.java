package com.thirty.insitereadservice.currenturl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUrlDto implements Comparable<CurrentUrlDto>{
    private int id;
    private String currentUrl;
    private int count;

    public static CurrentUrlDto create(String currentUrl,int count){
        return CurrentUrlDto.builder().currentUrl(currentUrl).count(count).build();
    }
    public CurrentUrlDto addId(int id){
        this.id=id;
        return this;
    }

    @Override
    public int compareTo(@NotNull CurrentUrlDto o) {
        return o.getCount()-this.getCount();
    }
}
