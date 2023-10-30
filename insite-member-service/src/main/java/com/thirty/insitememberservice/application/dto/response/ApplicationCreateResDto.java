package com.thirty.insitememberservice.application.dto.response;

import com.thirty.insitememberservice.application.dto.request.ApplicationCreateReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreateResDto {

    private int applicationId;

    public static ApplicationCreateResDto from(int applicationId){
        return ApplicationCreateResDto.builder()
                .applicationId(applicationId)
                .build();
    }


}
