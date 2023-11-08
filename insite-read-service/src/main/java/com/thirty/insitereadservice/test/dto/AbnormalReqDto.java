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
public class AbnormalReqDto {
    private String isRead;

    private String cookieId;

    private String currentUrl;

    private String applicationToken;

    private String applicationUrl;
}
