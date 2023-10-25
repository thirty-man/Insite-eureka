package com.thirty.ggulswriting.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchDto {

    private int id;

    private String roomTitle;

    private String masterName;

    private int memberCounts;

    private Boolean isOpen;

    public static RoomSearchDto of(int id, String roomTitle, String masterName, int memberCounts, Boolean isOpen){
        return RoomSearchDto.builder()
                .id(id)
                .roomTitle(roomTitle)
                .masterName(masterName)
                .memberCounts(memberCounts)
                .isOpen(isOpen)
                .build();
    }
}
