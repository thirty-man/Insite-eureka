package com.thirty.insiterealtimereadservice.buttons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPerUserDto implements Comparable<CountPerUserDto>{

    private int id;

    private String name;

    private int count;

    private double countPerUser;

    public static CountPerUserDto create(String name, int count, double countPerUser){
        return CountPerUserDto.builder()
            .name(name)
            .count(count)
            .countPerUser(countPerUser)
            .build();
    }

    public CountPerUserDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull CountPerUserDto o) {
        return o.count - this.getCount();
    }
}
