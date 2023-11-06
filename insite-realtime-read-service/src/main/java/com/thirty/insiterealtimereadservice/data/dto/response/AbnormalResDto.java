package com.thirty.insiterealtimereadservice.data.dto.response;

import com.thirty.insiterealtimereadservice.data.dto.AbnormalDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalResDto {

    private List<AbnormalDto> abnormalDtoList = new ArrayList<>();

    public static AbnormalResDto create(List<AbnormalDto> abnormalDtoList){
        return AbnormalResDto.builder()
            .abnormalDtoList(abnormalDtoList)
            .build();
    }
}
