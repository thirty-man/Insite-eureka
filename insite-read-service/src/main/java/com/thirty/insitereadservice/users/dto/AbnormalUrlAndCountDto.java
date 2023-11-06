package com.thirty.insitereadservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalUrlAndCountDto {

    private String url;

    private int count;

    public static AbnormalUrlAndCountDto create(String url, int count){
        return AbnormalUrlAndCountDto.builder()
            .url(url)
            .count(count)
            .build();
    }

    public void addCount(){
        this.count++;
    }
}
