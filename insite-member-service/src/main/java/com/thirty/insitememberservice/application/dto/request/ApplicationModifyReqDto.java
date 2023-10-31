package com.thirty.insitememberservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModifyReqDto {
    @NotNull
    private int applicationId;
    @NotBlank
    private String name;


}
