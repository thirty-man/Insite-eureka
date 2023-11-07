package com.thirty.insitereadservice.activeusers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OsActiveUserDto {
    private String os;
    private int count;
}
