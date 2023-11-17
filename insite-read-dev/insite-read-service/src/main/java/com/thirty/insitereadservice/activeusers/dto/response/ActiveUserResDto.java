package com.thirty.insitereadservice.activeusers.dto.response;

import com.thirty.insitereadservice.activeusers.dto.ActiveUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUserResDto {
    private List<ActiveUserDto> activeUserDtoList;

    public static ActiveUserResDto create(List<ActiveUserDto>activeUserDtoList){
        return ActiveUserResDto.builder().activeUserDtoList(activeUserDtoList).build();
    }
}
