package com.thirty.ggulswriting.room.dto.response;

import com.thirty.ggulswriting.room.dto.RoomDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResDto {

    private List<RoomDto> roomDtoList;

    public static RoomResDto from(List<RoomDto> roomDtoList){
        return RoomResDto.builder()
            .roomDtoList(roomDtoList)
            .build();
    }
}
