package com.thirty.insitememberservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCreateReqDto {

    @NotNull
    private String applicationUrl;

    @NotNull
    private String name;




}
