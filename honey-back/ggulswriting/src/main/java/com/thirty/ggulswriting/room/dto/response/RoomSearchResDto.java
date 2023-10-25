package com.thirty.ggulswriting.room.dto.response;

import com.thirty.ggulswriting.room.dto.RoomListDto;
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

    private List<RoomListDto> roomListDtoList;
    public static RoomSearchResDto from(List<RoomListDto> roomListDtoList){
        return RoomSearchResDto.builder()
                .roomListDtoList(roomListDtoList)
                .build();
    }
}
