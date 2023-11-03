package com.thirty.insitereadservice.feignclient.dto.request;

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
    private String applicationToken;

    @NotNull(message = "멤버 아이디를 기입해주세요")
    private int memberId;

    public static MemberValidReqDto create(String applicationToken, int memberId){

        return MemberValidReqDto.builder()
                .applicationToken(applicationToken)
                .memberId(memberId).build();
    }
}
