package com.thirty.insitereadservice.activeusers.dto.response;

import com.thirty.insitereadservice.activeusers.dto.ActiveTimeDto;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveUsersPerTimeResDto {
    // 0 ~ 5 = 밤, 6 ~ 11 = 아침, 12 ~ 17 = 오후, 18 ~ 23 = 저녁
    private static final int[] hourTo = {0,0,0,0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3};

    private int nightActiveUserCount; // value :0

    private int morningActiveUserCount; // value :1

    private int afternoonActiveUserCount; // value :2

    private int eveningActiveUserCount; // value :3

    public static ActiveUsersPerTimeResDto calculate(
        Map<String, ActiveTimeDto> activeIdWithActiveTimeMap){
        Map<Integer, Integer> hourIndexMap = new HashMap<>();

        for(int i = 0 ; i < 4; i++){
            hourIndexMap.put(i,0);
        }

        for(String activityId : activeIdWithActiveTimeMap.keySet()){

            LocalDateTime startDateTime = activeIdWithActiveTimeMap.get(activityId).getStartTime();
            LocalDateTime endDateTime = activeIdWithActiveTimeMap.get(activityId).getEndTime();

            //두 시간
            Duration duration = Duration.between(startDateTime, endDateTime);
            if(duration.toDays() > 1){
                for(int i = 0 ; i < 4; i++){
                    hourIndexMap.put(i,hourIndexMap.get(i)+1);
                }
            }else{
                int startTimeIndex = hourTo[startDateTime.toLocalTime().getHour()];
                int endTimeIndex = hourTo[endDateTime.toLocalTime().getHour()];

                if(duration.toDays() == 1){

                    if(startTimeIndex < endTimeIndex || startTimeIndex == endTimeIndex){
                        for(int i = 0 ; i < 4; i++){
                            hourIndexMap.put(i,hourIndexMap.get(i)+1);
                        }
                    }else{
                        for(int i = startTimeIndex ; i < 4 ; i++){
                            hourIndexMap.put(i,hourIndexMap.get(i)+1);
                        }
                        for(int i = 0 ; i <= endTimeIndex ; i++){
                            hourIndexMap.put(i,hourIndexMap.get(i)+1);
                        }
                    }
                    continue;
                }
                //날짜 차이가 0인 경우
                for(int i = startTimeIndex ; i <= endTimeIndex ; i++){
                    hourIndexMap.put(i,hourIndexMap.get(i)+1);
                }
            }
        }

        return ActiveUsersPerTimeResDto.builder()
            .nightActiveUserCount(hourIndexMap.get(0))
            .morningActiveUserCount(hourIndexMap.get(1))
            .afternoonActiveUserCount(hourIndexMap.get(2))
            .eveningActiveUserCount(hourIndexMap.get(3))
            .build();
    }
}
