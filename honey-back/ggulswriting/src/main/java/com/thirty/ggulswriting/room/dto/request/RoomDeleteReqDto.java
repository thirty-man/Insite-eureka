package com.thirty.ggulswriting.room.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDeleteReqDto {
    @NotNull
    private int roomId;
}
