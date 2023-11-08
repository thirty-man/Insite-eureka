package com.thirty.insitereadservice.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ButtonReqDto {

    private String name;

    private String currentUrl;

    private String cookieId;

    private String applicationToken;

    private String applicationUrl;

    private String activityId;

    private String requestCnt;
}
