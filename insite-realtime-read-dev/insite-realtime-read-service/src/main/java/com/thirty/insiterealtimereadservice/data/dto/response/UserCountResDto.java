package com.thirty.insiterealtimereadservice.data.dto.response;

import com.thirty.insiterealtimereadservice.data.dto.UserCountDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCountResDto {

    @Default
    List<UserCountDto> userCountDtoList = new ArrayList<>();

    public static UserCountResDto create(List<UserCountDto> userCountDtoList){
        return UserCountResDto.builder()
            .userCountDtoList(userCountDtoList)
            .build();
    }
}
