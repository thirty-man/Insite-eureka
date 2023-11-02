package com.thirty.insiterealtimereadservice.test.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalReqDto {
    private String isRead;

    @Default
    private LocalDateTime createTime = LocalDateTime.now();

    private String applicationToken;

    private String cookieId;

    private String currentUrl;

}
