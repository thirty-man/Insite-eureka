package com.thirty.insitereadservice.cumulativedata.flow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlFlowDto {
    private String beforeUrl;
    private int count;
}
