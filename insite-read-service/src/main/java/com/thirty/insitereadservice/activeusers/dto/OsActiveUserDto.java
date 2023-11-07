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
public class OsActiveUserDto implements Comparable<OsActiveUserDto>{
    private int id;
    private String os;
    private int count;

    public static OsActiveUserDto create(String os, int count){
        return OsActiveUserDto.builder()
            .os(os)
            .count(count)
            .build();
    }

    public OsActiveUserDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull OsActiveUserDto o) {
        return o.count - this.getCount();
    }
}
