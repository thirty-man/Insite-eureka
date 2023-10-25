package com.thirty.ggulswriting.room.dto.response;

import com.thirty.ggulswriting.member.dto.MemberDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomMemberResDto {
    @Default
    private List<MemberDto> memberDtoList = new ArrayList<>();

    public static RoomMemberResDto from(List<MemberDto> memberDtoList){
        return RoomMemberResDto.builder()
            .memberDtoList(memberDtoList)
            .build();
    }
}
