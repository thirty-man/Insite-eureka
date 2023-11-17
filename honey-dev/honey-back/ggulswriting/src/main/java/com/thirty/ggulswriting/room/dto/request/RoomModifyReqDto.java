package com.thirty.ggulswriting.room.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomModifyReqDto {

    @NotBlank
    private  String roomTitle;

    private String password;
}
