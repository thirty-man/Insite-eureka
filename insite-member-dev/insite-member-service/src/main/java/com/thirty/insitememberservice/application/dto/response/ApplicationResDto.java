package com.thirty.insitememberservice.application.dto.response;

import com.thirty.insitememberservice.application.dto.ApplicationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResDto {

    private List<ApplicationDto> applicationDtoList;

    public static ApplicationResDto from(List<ApplicationDto> applicationDtoList){
        return ApplicationResDto.builder()
                .applicationDtoList(applicationDtoList)
                .build();
    }
}
