package com.thirty.insitereadservice.activeusers.dto.response;

import com.thirty.insitereadservice.activeusers.dto.ActiveUserPerUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUserPerUserResDto {
    private List<ActiveUserPerUserDto> activeUserPerUserDtoList;
    public static ActiveUserPerUserResDto create(List<ActiveUserPerUserDto> activeUserPerUserDtoList){
        return ActiveUserPerUserResDto.builder().activeUserPerUserDtoList(activeUserPerUserDtoList).build();
    }
}
