package com.thirty.insitereadservice.flow.dto.request;

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
