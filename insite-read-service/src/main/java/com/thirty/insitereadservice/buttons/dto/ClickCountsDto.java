package com.thirty.insitereadservice.buttons.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickCountsDto implements Comparable<ClickCountsDto>{

    private int id;

    private int counts;

    private String date;

    public static ClickCountsDto create(String date, int counts){
        return ClickCountsDto.builder()
            .date(date)
            .counts(counts)
            .build();

    }

    public ClickCountsDto addId(int id){
        this.id = id;
        return this;
    }

    @Override
    public int compareTo(@NotNull ClickCountsDto o) {
        return o.counts - this.getCounts();
    }
}
