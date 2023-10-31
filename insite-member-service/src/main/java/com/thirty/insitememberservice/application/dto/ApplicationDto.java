package com.thirty.insitememberservice.application.dto;

import com.thirty.insitememberservice.application.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {
    private int applicationId;
    private String name;
    private String applicationUrl;
    private String applicationToken;

    public static ApplicationDto from(Application application){
        return ApplicationDto.builder()
                .applicationId(application.getApplicationId())
                .name(application.getName())
                .applicationUrl(application.getApplicationUrl())
                .applicationToken(application.getApplicationToken())
                .build();
    }
}
