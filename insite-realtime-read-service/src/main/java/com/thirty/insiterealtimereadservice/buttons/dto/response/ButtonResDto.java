package com.thirty.insiterealtimereadservice.buttons.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonResDto {

    private String name;

    private String currentUrl;

    private LocalDateTime createTime;

    private String cookieId;

    private String serviceToken;
}

