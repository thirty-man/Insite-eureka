package com.thirty.insiterealtimereadservice.data.measurement;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "data")
public class Data {

    @Column
    private String cookieId;

    @Column
    private String currentUrl;

    @Column
    private String beforeUrl;

    @Column
    private String referrer;

    @Column
    private String language;

    @Column
    private String responseTime;

    @Column
    private String osId;

    @Column
    private String isNew;

    @Column
    private String applicationToken;

    @Column
    private String applicationUrl;

    @Column
    private String activityId;
}
