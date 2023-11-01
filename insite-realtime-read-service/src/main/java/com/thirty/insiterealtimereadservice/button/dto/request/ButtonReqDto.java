package com.thirty.insiterealtimereadservice.button.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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

    @Default
    private LocalDateTime createTime = LocalDateTime.now();

    private String cookieId;

    private String serviceToken;
}
