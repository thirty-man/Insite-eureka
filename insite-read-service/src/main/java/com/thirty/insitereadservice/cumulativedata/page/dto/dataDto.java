package com.thirty.insitereadservice.cumulativedata.page.dto;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Measurement(name="data")
public class dataDto {
    @Column(name="applicationToken")
    private String applicationToken;
    @Column(name="cookieId")
    private String cookieId;
    @Column(name="currentUrl")
    private String currentUrl;
    @Column(name="activityId")
    private String activityId;
    @Column(name="beforeUrl")
    private String beforeUrl;
    @Column(name="responseTime")
    private String reponseTime;
    @Column(name = "deviceId")
    private String deviceId;
    @Column(name = "osId")
    private String osId;
    @Column(name="isNew")
    private boolean isNew;
    @Column(name = "createTime")
    private LocalDateTime createTime;
}
