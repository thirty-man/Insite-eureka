package com.thirty.insiterealtimereadservice.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalResDto {

    private boolean isAbnormal;

    public static AbnormalResDto create(boolean isAbnormal){
        return AbnormalResDto.builder()
            .isAbnormal(isAbnormal)
            .build();
    }
}
