package com.thirty.insitereadservice.common.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonReqDto {
    @NotNull(message = "통계 시작 시간을 기입해주세요.")
    private LocalDateTime startDateTime;

    @NotNull(message = "통계 끝 시간을 기입해주세요.")
    private LocalDateTime endDateTime;

    @NotNull(message = "앱 토큰을 기입해주세요.")
    private String applicationToken;
}
