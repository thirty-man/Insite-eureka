package com.thirty.insitereadservice.users.dto.response;

import com.thirty.insitereadservice.users.dto.UserCountDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCountResDto {
    private List<UserCountDto> userCountDtoList;
    public static UserCountResDto create(List<UserCountDto> userCountDtoList){
        return UserCountResDto.builder().userCountDtoList(userCountDtoList).build();
    }
}
