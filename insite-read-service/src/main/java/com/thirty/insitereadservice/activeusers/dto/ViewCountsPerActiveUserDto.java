package com.thirty.insitereadservice.activeusers.dto;

import com.thirty.insitereadservice.activeusers.dto.response.ViewCountsPerActiveUserResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewCountsPerActiveUserDto implements Comparable<ViewCountsPerActiveUserDto>{
    private int id;
    private String currentUrl;
    private int count;
    private double ratio;

    public static ViewCountsPerActiveUserDto create(String currentUrl, int count,int size){
        double ratio= (double)count/(double)size;
        return ViewCountsPerActiveUserDto.builder().currentUrl(currentUrl).count(count).ratio(ratio).build();
    }

    public ViewCountsPerActiveUserDto add(int id){
        this.id=id;
        return this;
    }


    @Override
    public int compareTo(@NotNull ViewCountsPerActiveUserDto o) {
        return o.getCount()-this.getCount();
    }
}
