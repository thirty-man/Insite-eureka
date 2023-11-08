package com.thirty.insitereadservice.users.dto;

import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageViewDto implements Comparable<PageViewDto> {
    private int id;
    private int pageView;
    private String currentUrl;


    public static PageViewDto create(String currentUrl,int pageView){
        return PageViewDto.builder()
                .currentUrl(currentUrl)
            .pageView(pageView)
            .build();
    }
    public PageViewDto addId(int id){
        this.id=id;
        return this;
    }

    @Override
    public int compareTo(@NotNull PageViewDto o) {
        return o.getPageView()-this.getPageView();
    }
}
