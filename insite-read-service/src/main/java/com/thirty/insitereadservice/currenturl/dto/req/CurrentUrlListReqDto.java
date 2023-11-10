package com.thirty.insitereadservice.currenturl.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUrlListReqDto {
    @NotNull(message = "통계 시작 시간을 기입해주세요.")
    private LocalDateTime startDateTime;

    @NotNull(message = "통계 끝 시간을 기입해주세요.")
    private LocalDateTime endDateTime;

    @NotNull(message = "앱 토큰을 기입해주세요.")
    private String applicationToken;
}
