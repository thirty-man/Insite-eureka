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
public class ActiveUserPerUserDto implements Comparable<ActiveUserPerUserDto>{
    private int id;
    private String currentUrl;
    private double activeUserPerUser;

    public static ActiveUserPerUserDto create(String currentUrl,double activeUserPerUser){
        return ActiveUserPerUserDto.builder().currentUrl(currentUrl).activeUserPerUser(activeUserPerUser).build();
    }
    public ActiveUserPerUserDto addId(int id){
        this.id=id;
        return this;
    }
    @Override
    public int compareTo(@NotNull ActiveUserPerUserDto o) {
        return o.getActiveUserPerUser()<=this.getActiveUserPerUser()?-1:1;
    }
}
