package com.thirty.insitereadservice.activeusers.dto.response;

import com.thirty.insitereadservice.activeusers.dto.OsActiveUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OsActiveUserResDto {
    private List<OsActiveUserDto> osActiveUserDtoList;

    public static OsActiveUserResDto from(List<OsActiveUserDto> list){
        return OsActiveUserResDto.builder().osActiveUserDtoList(list).build();
    }

}
