package com.thirty.insitereadservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookieIdUrlDto implements Comparable<CookieIdUrlDto>{
    private int id;
    private String cookieId;
    private int size;
    List<ViewCountsPerUserDto>viewCountsPerUserDtoList;
    public static CookieIdUrlDto create(String cookieId,List<ViewCountsPerUserDto>viewCountsPerUserDtoList){
        return CookieIdUrlDto.builder().cookieId(cookieId).viewCountsPerUserDtoList(viewCountsPerUserDtoList).build();

    }
    public CookieIdUrlDto addSize(int size){
        for(int i=0;i<this.viewCountsPerUserDtoList.size();i++){
            this.viewCountsPerUserDtoList.get(i).setRatio(size);
        }
        return this;
    }
    public CookieIdUrlDto addId(int id){
        this.id=id;
        return this;
    }

    @Override
    public int compareTo(@NotNull CookieIdUrlDto o) {
        return o.getSize()-this.getSize();
    }
}
