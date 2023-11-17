package com.thirty.ggulswriting.room.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateResDto {

    private int roomId;

    public static RoomCreateResDto from(int roomId){
        return RoomCreateResDto.builder()
                .roomId(roomId)
                .build();
    }
}
