package com.thirty.insitereadservice.activeusers.dto.response;

import com.thirty.insitereadservice.activeusers.dto.AverageActiveTimeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageActiveTimeResDto {
    private List<AverageActiveTimeDto> averageActiveTimeDtoList;

    public static AverageActiveTimeResDto create(List<AverageActiveTimeDto> averageActiveTimeDtoList){
        return AverageActiveTimeResDto.builder().averageActiveTimeDtoList(averageActiveTimeDtoList).build();
    }
}
