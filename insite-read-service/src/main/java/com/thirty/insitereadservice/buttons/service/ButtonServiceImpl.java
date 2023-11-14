package com.thirty.insitereadservice.buttons.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.thirty.insitereadservice.buttons.builder.ButtonsQueryBuilder;
import com.thirty.insitereadservice.buttons.dto.ButtonLogDto;
import com.thirty.insitereadservice.buttons.dto.ButtonRateDto;
import com.thirty.insitereadservice.buttons.dto.request.ButtonAbnormalReqDto;
import com.thirty.insitereadservice.buttons.dto.request.EveryButtonRateReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ButtonLogsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ButtonAbnormalResDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsResDto;
import com.thirty.insitereadservice.buttons.dto.ClickCountsDto;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.EveryButtonRateResDto;
import com.thirty.insitereadservice.buttons.dto.response.ButtonLogsResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.ButtonDto;
import com.thirty.insitereadservice.feignclient.dto.request.ButtonListReqDto;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insitereadservice.feignclient.dto.response.ButtonListResDto;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.ButtonException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService{
    private final MemberServiceClient memberServiceClient;
    private final ButtonsQueryBuilder buttonsQueryBuilder;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ClickCountsResDto getClickCounts(ClickCountsReqDto clickCountsReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(clickCountsReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = buttonsQueryBuilder.getClickCounts(
            clickCountsReqDto.getStartDateTime(),
            clickCountsReqDto.getEndDateTime(),
            clickCountsReqDto.getApplicationToken(),
            clickCountsReqDto.getButtonName()
        );

        List<FluxTable> tables = queryApi.query(query.toString());
        return ClickCountsResDto.create(getClickCountsDtoList(tables));
    }

    @Override
    public ButtonLogsResDto getButtonLogs(ButtonLogsReqDto buttonLogsReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(buttonLogsReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = buttonsQueryBuilder.getButtonClickActiveUsersDesc(
            buttonLogsReqDto.getStartDateTime(),
            buttonLogsReqDto.getEndDateTime(),
            buttonLogsReqDto.getApplicationToken(),
            buttonLogsReqDto.getButtonName()
        );

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, String> userButtonClickTime = getUserButtonClickTime(tables);

        //전체 사용자가 없는경우 바로 0리턴
        double totalButtonClickUsers = tables.size();
        if(totalButtonClickUsers == 0){
            return ButtonLogsResDto.create(0,0,new ArrayList<>());
        }

        List<ButtonLogDto> buttonLogDtoList = getButtonLogDtoList(tables);
        double totalButtonClicks = buttonLogDtoList.size();
        Flux dataQuery = buttonsQueryBuilder.getLastActive(
            buttonLogsReqDto.getStartDateTime(),
            buttonLogsReqDto.getEndDateTime(),
            buttonLogsReqDto.getApplicationToken()
        );

        List<FluxTable> dataTables = queryApi.query(dataQuery.toString());
        Map<String, String> userLastTime = getUserLastTime(dataTables);
        double exitUsersAfterButtonClick = calculateExitUsers(userButtonClickTime, userLastTime);
        return ButtonLogsResDto.create(
            exitUsersAfterButtonClick/totalButtonClickUsers,
            totalButtonClicks/totalButtonClickUsers,
            buttonLogDtoList
        );
    }

    @Override
    public List<ButtonAbnormalResDto> getButtonAbnormal(ButtonAbnormalReqDto buttonAbnormalReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(buttonAbnormalReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();
        String query = buttonsQueryBuilder.getButtonAbnormal(
            buttonAbnormalReqDto.getStartDateTime(),
            buttonAbnormalReqDto.getEndDateTime(),
            buttonAbnormalReqDto.getApplicationToken()
        );

        List<FluxTable> tables = queryApi.query(query);
        return getButtonAbnormalResDtoList(tables);
    }

    @Override
    public EveryButtonRateResDto getEveryButtonRate(EveryButtonRateReqDto everyButtonDistReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(everyButtonDistReqDto.getApplicationToken(),memberId));

        //멤버의 모든 버튼 조회
        List<ButtonRateDto> buttonRateDtoList = getButtonRateDtosButtonName(
            everyButtonDistReqDto, memberId);

        //모든 버튼이 눌린횟수 / 버튼 종류 = avg, 평균 - 각 버튼 눌린 횟수 = 증감량
        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = buttonsQueryBuilder.getEveryButtonClickCountsDesc(
            everyButtonDistReqDto.getStartDateTime(),
            everyButtonDistReqDto.getEndDateTime(),
            everyButtonDistReqDto.getApplicationToken()
        );
        List<FluxTable> tables = queryApi.query(query.toString());

        int clickedButtons = tables.size();
        if(clickedButtons == 0){
            return EveryButtonRateResDto.create(0.0, buttonRateDtoList);
        }

        double totalCLickCounts = 0.0;
        for(FluxTable fluxTable : tables){
            totalCLickCounts += fluxTable.getRecords().size();
        }
        //각 버튼 클릭 수, 증감률
        double totalAvg = totalCLickCounts/clickedButtons;

        Map<String, Integer> buttonNameWithClickCounts = getStringIntegerMap(tables);

        for(String buttonName : buttonNameWithClickCounts.keySet()){
            int count = buttonNameWithClickCounts.get(buttonName);
            double increaseDecreaseRate = ((count - totalAvg)/ totalAvg);

            for(ButtonRateDto buttonRateDto : buttonRateDtoList){

                if(buttonRateDto.getName().equals(buttonName)){
                    buttonRateDto.saveValues(count, increaseDecreaseRate);
                }
            }
        }
        //카운트내림차순으로 정렬
        Collections.sort(buttonRateDtoList, new Comparator<ButtonRateDto>() {
            @Override
            public int compare(ButtonRateDto dto1, ButtonRateDto dto2) {
                return dto2.getClickCounts() - dto1.getClickCounts();
            }
        });

        return EveryButtonRateResDto.create(totalAvg, buttonRateDtoList);
    }



    private double calculateExitUsers(Map<String,String> userButtonClickTime, Map<String, String> userLastTime){
        // 비교하는데 button 누른시간 + 30분 이 마지막인경우 접속 종료자
        // 버튼 누른시간이 현재시간 -30분 보다 최근인경우 접속 중
        double count = 0;
        for(String activityId: userButtonClickTime.keySet()){
            if(userLastTime.containsKey(activityId)) {
                log.info("키 값={}", activityId);
                String stringValueOfButtonClick = userButtonClickTime.get(activityId);
                String stringValueOfUserLastTime = userLastTime.get(activityId);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime buttonClickDateTimePlusActiveMinutes = LocalDateTime.parse(stringValueOfButtonClick, formatter).plusMinutes(30);
                LocalDateTime userLastDateTimePlusActiveMinutes = LocalDateTime.parse(stringValueOfUserLastTime, formatter).plusMinutes(30);
                LocalDateTime now = LocalDateTime.now();

                //유저 마지막 시간+30분이 현재시간 이후인 경우 접속중
                if(now.isBefore(userLastDateTimePlusActiveMinutes) || now.equals(userLastDateTimePlusActiveMinutes)){
                    continue;
                }
                //버튼 누른 시간+30분이 현재시간 이후인 경우 접속중
                if(now.isBefore(buttonClickDateTimePlusActiveMinutes) || now.equals(buttonClickDateTimePlusActiveMinutes)){
                    continue;
                }
                // 페이지 이동시간이 더 과거인경우 count++
                if (userLastDateTimePlusActiveMinutes.isBefore(buttonClickDateTimePlusActiveMinutes) || userLastDateTimePlusActiveMinutes.equals(buttonClickDateTimePlusActiveMinutes)) {
                    //버튼을 마지막으로 누른사람
                    count++;
                }
            }
        }
        return count;
    }

    @NotNull
    private List<ClickCountsDto> getClickCountsDtoList(List<FluxTable> tables) {
        Map<String, Integer> countsMap = new LinkedHashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                String date = records.get(0).getValueByKey("_time").toString().split("T")[0];
                String countStringValue = records.get(0).getValueByKey("_value").toString();
                int count = Integer.valueOf(countStringValue);

                // Map에 이름(name)과 해당 이름의 카운트 값을 저장
                if (countsMap.containsKey(date)) {
                    countsMap.put(date, countsMap.get(date) + count);
                } else {
                    countsMap.put(date, count);
                }
            }
        }

        //response 형식으로 변환
        List<ClickCountsDto> countDtoList = new ArrayList<>();
        int id = 0;

        for(String date : countsMap.keySet()){
            countDtoList.add(ClickCountsDto.create(date, countsMap.get(date)).addId(id++));
        }
        return countDtoList;
    }

    @NotNull
    private Map<String, String> getUserButtonClickTime(List<FluxTable> tables) {
        Map<String,String> userButtonClickTime = new HashMap<>();

        for(FluxTable fluxTable : tables){
            //이탈율 계산을 위한 map
            String activityId = fluxTable.getRecords().get(0).getValueByKey("activityId").toString();
            String timeStringValue = fluxTable.getRecords().get(0).getValueByKey("_time").toString();

            userButtonClickTime.put(activityId, timeStringValue);
        }
        return userButtonClickTime;
    }

    @NotNull
    private List<ButtonLogDto> getButtonLogDtoList(List<FluxTable> tables) {
        List<ButtonLogDto> buttonLogDtoList = new ArrayList<>();

        int id = 0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                //log 출력을 위해 dto 생성
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime time = LocalDateTime.parse(record.getValueByKey("_time").toString(), formatter);

                String currentUrl = record.getValueByKey("currentUrl").toString();
                String cookieId = record.getValueByKey("cookieId").toString();
                String stringValueOfRequestCnt = record.getValueByKey("requestCnt").toString();
                int requestCnt = Integer.valueOf(stringValueOfRequestCnt);

                buttonLogDtoList.add(ButtonLogDto.create(currentUrl,time,cookieId, requestCnt >= 10).addId(id++));
            }
        }
        //클릭 시간 오름차순으로 정렬
        Collections.sort(buttonLogDtoList, new Comparator<ButtonLogDto>(){
            @Override
            public int compare(ButtonLogDto dto1, ButtonLogDto dto2) {
                return dto1.getClickDateTime().compareTo(dto2.getClickDateTime());
            }
        });
        return buttonLogDtoList;
    }

    @NotNull
    private Map<String, String> getUserLastTime(List<FluxTable> dataTables) {
        Map<String, String> userLastTime = new HashMap<>();

        for (FluxTable fluxTable : dataTables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                String activityId = record.getValueByKey("activityId").toString();
                String timeStringValue = record.getValueByKey("_time").toString();

                userLastTime.put(activityId, timeStringValue);
            }
        }
        return userLastTime;
    }

    @NotNull
    private List<ButtonAbnormalResDto> getButtonAbnormalResDtoList(List<FluxTable> tables) {
        List<ButtonAbnormalResDto> buttonAbnormalResDtoList = new ArrayList<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime currentDateTime = LocalDateTime.parse(record.getValueByKey("_time").toString(), formatter);

                String buttonName = record.getValueByKey("name").toString();
                String cookieId = record.getValueByKey("cookieId").toString();
                String currentUrl = record.getValueByKey("currentUrl").toString();
                String stringValueOfRequestCnt = record.getValueByKey("requestCnt").toString();
                int requestCnt = Integer.valueOf(stringValueOfRequestCnt);

                buttonAbnormalResDtoList.add(ButtonAbnormalResDto.create(cookieId, buttonName, currentDateTime,currentUrl,requestCnt));
            }
        }
        return buttonAbnormalResDtoList;
    }

    @NotNull
    private List<ButtonRateDto> getButtonRateDtosButtonName(EveryButtonRateReqDto everyButtonDistReqDto,
        int memberId) {
        ButtonListResDto buttons = memberServiceClient.getMyButtonList(ButtonListReqDto.create(
            everyButtonDistReqDto.getApplicationToken()), memberId);

        //각 버튼 Res 생성
        List<ButtonDto> buttonDtoList = buttons.getButtonDtoList();
        if(buttonDtoList.size() == 0){
            throw new ButtonException(ErrorCode.NOT_EXIST_BUTTON);
        }

        List<ButtonRateDto> buttonRateDtoList = new ArrayList<>();
        int id = 0;
        for(ButtonDto buttonDto : buttonDtoList){
            buttonRateDtoList.add(ButtonRateDto.create(buttonDto.getName(),0,0.0).addId(id++));
            log.info("buttonName={}",buttonDto.getName());
        }
        return buttonRateDtoList;
    }

    @NotNull
    private Map<String, Integer> getStringIntegerMap(List<FluxTable> tables) {
        Map<String, Integer> buttonNameWithClickCounts = new HashMap<>();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            buttonNameWithClickCounts.put(records.get(0).getValueByKey("name").toString(), records.size());
        }
        return buttonNameWithClickCounts;
    }
}
