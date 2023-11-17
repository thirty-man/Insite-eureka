package com.thirty.insitereadservice.activeusers.dto.response;

import com.thirty.insitereadservice.activeusers.dto.ViewCountsPerActiveUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewCountsPerActiveUserResDto {
    private List<ViewCountsPerActiveUserDto> viewCountsPerActiveUserDtoList;
    public static ViewCountsPerActiveUserResDto create(List<ViewCountsPerActiveUserDto> viewCountsPerActiveUserDtoList){
        return ViewCountsPerActiveUserResDto.builder().viewCountsPerActiveUserDtoList(viewCountsPerActiveUserDtoList).build();
    }
}
