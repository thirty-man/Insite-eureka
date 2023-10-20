package com.thirty.ggulswriting.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private int id;

    private String name;

    public static MemberDto of(int id, String name){
        return MemberDto.builder()
            .id(id)
            .name(name)
            .build();
    }
}
