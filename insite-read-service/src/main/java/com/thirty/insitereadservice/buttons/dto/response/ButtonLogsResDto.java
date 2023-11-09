package com.thirty.insitereadservice.buttons.dto.response;

import com.thirty.insitereadservice.buttons.dto.ButtonLogDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonLogsResDto {

    private double exitRate;

    private double clickCountsPerActiveUsers;

    @Default
    private List<ButtonLogDto> buttonLogDtoList = new ArrayList<>();

    public static ButtonLogsResDto create(double exitRate, double clickCountsPerActiveUsers, List<ButtonLogDto> buttonLogDtoList){
        return ButtonLogsResDto.builder()
            .exitRate(exitRate)
            .clickCountsPerActiveUsers(clickCountsPerActiveUsers)
            .buttonLogDtoList(buttonLogDtoList)
            .build();
    }
}
