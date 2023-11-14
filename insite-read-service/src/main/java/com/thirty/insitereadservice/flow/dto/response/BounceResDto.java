package com.thirty.insitereadservice.flow.dto.response;

import com.thirty.insitereadservice.flow.dto.BounceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BounceResDto {
    private List<BounceDto> bounceDtoList;

    public static BounceResDto create(List<BounceDto> bounceDtoList){
        return BounceResDto.builder().bounceDtoList(bounceDtoList).build();
    }
}
