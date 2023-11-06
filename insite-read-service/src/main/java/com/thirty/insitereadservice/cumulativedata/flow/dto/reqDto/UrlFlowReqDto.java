package com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlFlowReqDto {
    private String applicationToken;
    private String currentUrl;
}
