package com.thirty.insitememberservice.application.dto;

import com.thirty.insitememberservice.application.entity.Application;
import java.time.LocalDateTime;
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
    private LocalDateTime createTime;

    public static ApplicationDto from(Application application){
        return ApplicationDto.builder()
                .applicationId(application.getApplicationId())
                .name(application.getName())
                .applicationUrl(application.getApplicationUrl())
                .applicationToken(application.getApplicationToken())
                .createTime(application.getCreatedTime())
                .build();
    }
}
