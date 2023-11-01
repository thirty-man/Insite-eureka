package com.thirty.insitereadservice.cumulativedata.page.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DataDto {
    private String applicationToken;
    private String cookieId;
    private String currentUrl;
    private String activityId;
    private String beforeUrl;
    private int reponseTime;
    private String osId;
    private boolean isNew;
    private LocalDateTime createTime;



}
