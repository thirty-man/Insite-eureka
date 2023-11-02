package com.thirty.insiterealtimereadservice.test.dto;

import com.influxdb.annotations.Measurement;
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
public class DataReqDto {

    private String cookieId;

    private String currentUrl;

    private String beforeUrl;

    private int responseTime;

    private String osId;

    @Default
    private LocalDateTime createTime = LocalDateTime.now();

    private Boolean isNew;

    private String applicationToken;

    private String activityId;
}
