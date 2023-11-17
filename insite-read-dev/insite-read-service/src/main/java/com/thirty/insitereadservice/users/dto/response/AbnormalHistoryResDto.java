package com.thirty.insitereadservice.users.dto.response;

import com.thirty.insitereadservice.users.dto.AbnormalDto;
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
public class AbnormalHistoryResDto {


    @Default
    private List<AbnormalDto> abnormalDtoList = new ArrayList<>();

    public static AbnormalHistoryResDto create(List<AbnormalDto> abnormalDtoList){
        return AbnormalHistoryResDto.builder()
            .abnormalDtoList(abnormalDtoList)
            .build();
    }
}
