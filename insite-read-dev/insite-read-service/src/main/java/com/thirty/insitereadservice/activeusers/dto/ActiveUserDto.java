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
public class ActiveUserDto implements Comparable<ActiveUserDto>{
    private int id;
    private String currentUrl;
    private int activeUserCount;
    private double ratio;


    public static ActiveUserDto create (String currentUrl,int activeUserCount){
        return ActiveUserDto.builder()
                .currentUrl(currentUrl)
            .activeUserCount(activeUserCount)
            .build();
    }
    public void addCount(){
        ++this.activeUserCount;
    }
    public ActiveUserDto add(int id,double size){
        this.ratio= (double)activeUserCount/size;
        this.id=id;
        return this;
    }

    @Override
    public int compareTo(@NotNull ActiveUserDto o) {
        return o.getActiveUserCount()-this.getActiveUserCount();
    }
}
