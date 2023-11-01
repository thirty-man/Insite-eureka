package com.thirty.insiterealtimereadservice.test.dto;

import com.influxdb.annotations.Measurement;
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
public class DataReqDto {

    private String cookieId;

    private String currentUrl;

    private String beforeUrl;

    private int responseTime;

    private String deviceId;

    private String osId;

    @Default
    private LocalDateTime createTime = LocalDateTime.now();

    private Boolean isNew;

    private String serviceToken;

    private String activityId;
}
