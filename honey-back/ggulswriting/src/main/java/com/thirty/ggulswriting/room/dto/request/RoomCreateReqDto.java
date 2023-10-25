package com.thirty.ggulswriting.room.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateReqDto {
    @NotBlank( message = "방 제목 입력 오류입니다." )
    private String roomTitle;

    @Future( message = "현재 시간보다 커야 합니다.")
    private LocalDateTime showTime;

    private String password;

}
