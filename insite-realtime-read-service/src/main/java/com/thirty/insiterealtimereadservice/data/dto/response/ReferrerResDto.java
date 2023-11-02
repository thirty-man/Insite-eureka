package com.thirty.insiterealtimereadservice.data.dto.response;

import com.thirty.insiterealtimereadservice.data.dto.ReferrerDto;
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
public class ReferrerResDto {

    @Default
    List<ReferrerDto> referrerDtoList = new ArrayList<>();

    public static ReferrerResDto create(List<ReferrerDto> referrerDtoList){
        return ReferrerResDto.builder()
            .referrerDtoList(referrerDtoList)
            .build();
    }
}
