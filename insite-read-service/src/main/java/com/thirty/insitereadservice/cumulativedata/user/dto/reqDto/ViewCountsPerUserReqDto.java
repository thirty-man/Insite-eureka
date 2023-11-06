package com.thirty.insitereadservice.cumulativedata.user.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewCountsPerUserReqDto {
    private String applicationToken;
    private String currentUrl;
}
