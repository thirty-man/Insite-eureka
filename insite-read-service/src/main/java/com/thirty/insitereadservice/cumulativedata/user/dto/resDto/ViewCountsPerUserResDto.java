package com.thirty.insitereadservice.cumulativedata.user.dto.resDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewCountsPerUserResDto {
    private double viewCountsPerUser;
}
