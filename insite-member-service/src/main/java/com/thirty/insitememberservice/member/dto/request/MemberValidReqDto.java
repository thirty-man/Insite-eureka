package com.thirty.insitememberservice.member.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberValidReqDto {
    @NotNull(message = "토큰값을 기입해주세요")
    private String token;

    @NotNull(message = "멤버 아이디를 기입해주세요")
    private int memberId;
}
