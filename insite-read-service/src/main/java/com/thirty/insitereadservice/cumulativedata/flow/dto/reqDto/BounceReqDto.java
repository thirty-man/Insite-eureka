package com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BounceReqDto {
    private String applicationToken;
    private String currentUrl;
}
