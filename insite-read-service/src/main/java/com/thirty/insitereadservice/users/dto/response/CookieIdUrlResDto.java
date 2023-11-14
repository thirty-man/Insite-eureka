package com.thirty.insitereadservice.users.dto.response;

import com.thirty.insitereadservice.users.dto.CookieIdUrlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookieIdUrlResDto {
    private List<CookieIdUrlDto>cookieIdUrlDtoList;
    public static CookieIdUrlResDto create(List<CookieIdUrlDto>cookieIdUrlDtoList){
        return CookieIdUrlResDto.builder().cookieIdUrlDtoList(cookieIdUrlDtoList).build();
    }
}
