package com.thirty.ggulswriting.room.dto.response;

import com.thirty.ggulswriting.room.dto.RoomSearchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchResDto {

    private List<RoomSearchDto> roomSearchDtoList;

    int totalCount;

    public static RoomSearchResDto from(List<RoomSearchDto> roomSearchDtoList, int totalCount){
        return RoomSearchResDto.builder()
                .roomSearchDtoList(roomSearchDtoList)
                .totalCount(totalCount)
                .build();
    }
}
