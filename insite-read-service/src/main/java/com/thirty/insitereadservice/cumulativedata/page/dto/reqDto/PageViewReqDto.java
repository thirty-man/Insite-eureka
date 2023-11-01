package com.thirty.insitereadservice.cumulativedata.page.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageViewReqDto {
    private String applicationToken;

    private String currentUrl;

}
