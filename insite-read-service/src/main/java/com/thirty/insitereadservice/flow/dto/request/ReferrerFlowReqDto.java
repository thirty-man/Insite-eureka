package com.thirty.insitereadservice.flow.dto.request;

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
public class ReferrerFlowReqDto {
    @NotNull(message = "시작시간을 기입해 주세요")
    private LocalDateTime startDateTime;

    @NotNull(message = "끝 시간을 기입해 주세요")
    private LocalDateTime endDateTime;

    @NotNull(message = "앱 토큰을 기입해 주세요")
    private String applicationToken;

}
