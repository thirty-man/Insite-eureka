package com.thirty.insitereadservice.activeusers.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveTimeDto {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public static ActiveTimeDto create(LocalDateTime starttime, LocalDateTime endTime){
        return ActiveTimeDto.builder()
            .startTime(starttime)
            .endTime(endTime)
            .build();
    }
}
