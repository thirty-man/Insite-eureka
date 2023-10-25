package com.thirty.ggulswriting.room.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailResDto {

    private String masterName;

    private String roomTitle;

    private Boolean isOpen;

    private LocalDateTime showTime;

    public static RoomDetailResDto of(String masterName, String roomTitle, Boolean isOpen, LocalDateTime showTime){
        return RoomDetailResDto.builder()
            .masterName(masterName)
            .roomTitle(roomTitle)
            .isOpen(isOpen)
            .showTime(showTime)
            .build();
    }
}
