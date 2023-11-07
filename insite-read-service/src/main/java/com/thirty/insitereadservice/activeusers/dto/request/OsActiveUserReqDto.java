package com.thirty.insitereadservice.activeusers.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OsActiveUserReqDto {

    @NotNull(message = "통계 시작 시간을 기입해주세요.")
    private LocalDateTime startDateTime;

    @NotNull(message = "통계 끝 시간을 기입해주세요.")
    private LocalDateTime endDateTime;

    @NotNull(message = "앱 토큰을 기입해주세요.")
    private String applicationToken;

}
