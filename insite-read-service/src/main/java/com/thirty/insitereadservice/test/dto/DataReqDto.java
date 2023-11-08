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
public class DataReqDto {

    private String cookieId;

    private String currentUrl;

    private String beforeUrl;

    private String referrer;

    private String language;

    private String responseTime;

    private String osId;

    private String isNew;

    private String requestCnt;

    private String applicationToken;

    private String applicationUrl;

    private String activityId;
}
