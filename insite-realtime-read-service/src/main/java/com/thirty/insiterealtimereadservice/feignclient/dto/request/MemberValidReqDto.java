package com.thirty.insiterealtimereadservice.feignclient.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberValidReqDto {
    @NotNull(message = "토큰값을 기입해주세요")
    private String token;

    public static MemberValidReqDto create(String token){
        return MemberValidReqDto.builder()
            .token(token)
            .build();
    }
}
