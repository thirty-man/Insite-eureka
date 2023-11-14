package com.thirty.insitereadservice.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCountDto implements Comparable<UserCountDto>{
    private int id;
    private String currentUrl;
    private int userCount;

    public static UserCountDto create(String currentUrl,int userCount){
        return UserCountDto.builder()
                .currentUrl(currentUrl)
            .userCount(userCount)
            .build();
    }
    public UserCountDto addId(int id){
        this.id=id;
        return this;
    }

    @Override
    public int compareTo(@NotNull UserCountDto o) {
        return o.getUserCount()-this.getUserCount();
    }
}
