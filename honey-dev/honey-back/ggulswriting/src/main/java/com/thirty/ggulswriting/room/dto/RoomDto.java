package com.thirty.ggulswriting.room.dto;

import com.thirty.ggulswriting.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private int id;

    private String roomTitle;

    private LocalDateTime showTime;

    public static RoomDto from(Room room){
        return RoomDto.builder()
            .id(room.getRoomId())
            .roomTitle(room.getRoomTitle())
            .showTime(room.getShowTime())
            .build();
    }
}
