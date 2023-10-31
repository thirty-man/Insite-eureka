package com.thirty.insitememberservice.application.dto.response;

import com.thirty.insitememberservice.application.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTokenResDto {
   private String applicationToken;

   public static ApplicationTokenResDto from(Application application){
       return ApplicationTokenResDto.builder()
               .applicationToken(application.getApplicationToken())
               .build();
   }
}
