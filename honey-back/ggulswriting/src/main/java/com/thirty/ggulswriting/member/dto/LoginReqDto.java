package com.thirty.ggulswriting.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginReqDto {

    @NotEmpty
    private String code;
}
