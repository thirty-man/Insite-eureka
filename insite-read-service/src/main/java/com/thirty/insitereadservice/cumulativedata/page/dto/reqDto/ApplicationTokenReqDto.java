package com.thirty.insitereadservice.cumulativedata.page.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTokenReqDto {
    private String applicationToken;
}
