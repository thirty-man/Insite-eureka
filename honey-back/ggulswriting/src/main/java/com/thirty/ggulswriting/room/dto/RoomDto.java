package com.thirty.ggulswriting.room.dto;

import com.thirty.ggulswriting.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private int id;

    private String title;

    public static RoomDto from(Room room){
        return RoomDto.builder()
            .id(room.getRoomId())
            .title(room.getRoomTitle())
            .build();
    }
}
