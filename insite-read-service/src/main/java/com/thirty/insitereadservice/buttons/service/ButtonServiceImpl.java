package com.thirty.insitereadservice.buttons.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
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
import com.thirty.insitereadservice.feignclient.dto.response.ButtonListResDto;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.ButtonException;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService{
    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ClickCountsResDto getClickCounts(ClickCountsReqDto clickCountsReqDto, int memberId) {
        String token = clickCountsReqDto.getApplicationToken();

//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));
        
        //통계 시간 설정
        Instant startInstant = clickCountsReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = clickCountsReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("name").equal(clickCountsReqDto.getButtonName())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy(new String[]{"_time", "name"})
            .sort(new String[]{"_time"})
            .count();

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장

        List<FluxTable> tables = queryApi.query(query.toString());
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

        return ClickCountsResDto.create(countDtoList);
    }

    @Override
    public ButtonLogsResDto getButtonLogs(ButtonLogsReqDto buttonLogsReqDto, int memberId) {
        String token = buttonLogsReqDto.getApplicationToken();

//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        //통계 시간 설정
        Instant startInstant = buttonLogsReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = buttonLogsReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //해당 버튼을 누른 전체 사용자 수
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("name").equal(buttonLogsReqDto.getButtonName())
        );

        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String,String> userButtonClickTime = new HashMap<>();

        List<ButtonLogDto> buttonLogDtoList = new ArrayList<>();
        double totalButtonClickUsers = tables.size();

        //전체 사용자가 없는경우 바로 0리턴
        if(totalButtonClickUsers == 0){
            return ButtonLogsResDto.create(0,0,buttonLogDtoList);
        }

        double buttonClicks = 0;
        int id = 0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            buttonClicks += records.size();
            for (FluxRecord record : records) {
                //이탈율 계산을 위한 map
                String activityId = record.getValueByKey("activityId").toString();
                String timeStringValue = record.getValueByKey("_time").toString();

                userButtonClickTime.put(activityId, timeStringValue);

                //log 출력을 위해 dto 생성
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime time = LocalDateTime.parse(timeStringValue, formatter);

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

        // data에서 모든 activityId의 마지막 활동 시간을 조회한다
        // 위의 맵과 일치하는 아이디를 선별한다

        Restrictions dataRestrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux dataQuery = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(dataRestrictions)
            .groupBy("activityId")
            .sort(new String[]{"_time"});

        log.info("dataQuery ={}", dataQuery);
        List<FluxTable> dataTables = queryApi.query(dataQuery.toString());
        Map<String, String> userLastTime = new HashMap<>();

        for (FluxTable fluxTable : dataTables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                String activityId = record.getValueByKey("activityId").toString();
                String timeStringValue = record.getValueByKey("_time").toString();

                userLastTime.put(activityId, timeStringValue);
            }
        }

        double exitUsersAfterButtonClick = calculateExitUsers(userButtonClickTime, userLastTime);

        return ButtonLogsResDto.create(
            exitUsersAfterButtonClick/totalButtonClickUsers,
            buttonClicks/totalButtonClickUsers,
            buttonLogDtoList
        );
    }

    @Override
    public List<ButtonAbnormalResDto> getButtonAbnormal(ButtonAbnormalReqDto buttonAbnormalReqDto,
        int memberId) {
        String applicationToken = buttonAbnormalReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken,memberId));

        //통계 시간 설정
        Instant startInstant = buttonAbnormalReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = buttonAbnormalReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        QueryApi queryApi = influxDBClient.getQueryApi();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from(bucket: \"").append(bucket).append("\")\n");
        queryBuilder.append("  |> range(start: ").append(startInstant).append(", stop:").append(endInstant).append(")\n");
        queryBuilder.append("  |> filter(fn: (r) => r._measurement == \"button\" and r.applicationToken == \"")
            .append(applicationToken).append("\" and float(v: r.requestCnt) >= 10)\n");
        queryBuilder.append("  |> group(columns:[\"\"])\n");
        queryBuilder.append("  |> sort(columns: [\"_time\"])");


        log.info("query={}",queryBuilder);

        List<FluxTable> tables = queryApi.query(queryBuilder.toString());
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

    @Override
    public EveryButtonRateResDto getEveryButtonRate(EveryButtonRateReqDto everyButtonDistReqDto,
        int memberId) {

        String applicationToken = everyButtonDistReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken,memberId));

        //통계 시간 설정
        Instant startInstant = everyButtonDistReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = everyButtonDistReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //멤버의 모든 버튼 조회
        ButtonListResDto buttons = memberServiceClient.getMyButtonList(ButtonListReqDto.create(applicationToken), memberId);

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

        //모든 버튼이 눌린횟수 / 버튼 종류 = avg, 평균 - 각 버튼 눌린 횟수 = 증감량
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions dataRestrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(dataRestrictions)
            .groupBy("name")
            .sort(new String[]{"_time"}, true);

        log.info("query ={}", query);

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> buttonNameWithClickCounts = new HashMap<>();

        int clickedButtons = tables.size();
        if(clickedButtons == 0){
            return EveryButtonRateResDto.create(0.0, buttonRateDtoList);
        }
        double totalCLickCounts = 0.0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            totalCLickCounts += records.size();

            buttonNameWithClickCounts.put(records.get(0).getValueByKey("name").toString(), records.size());
        }

        //각 버튼 클릭 수, 증감률
        double totalAvg = totalCLickCounts/clickedButtons;

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

    private double calculateFirstClickTimeAvg(Map<String,String> userFirstClickTime, Map<String,String> userFirstVisit){
        double sum = 0.0;
        for (String activityId : userFirstClickTime.keySet()){

            if(userFirstVisit.containsKey(activityId)){
                String buttonClickTime = userFirstClickTime.get(activityId);
                String userVisitTime = userFirstVisit.get(activityId);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                log.info("검색 대상 = {}" , activityId);
                Date buttonClickDate;
                Date userVisitDate;
                try {
                    buttonClickDate = dateFormat.parse(buttonClickTime);
                    userVisitDate = dateFormat.parse(userVisitTime);
                    log.info("버튼 클릭 시간 = {}", buttonClickDate.getTime());
                    log.info("처음 방문  시간 = {}", userVisitDate.getTime());

                    // 두 시간 간의 차이 구하기(초 단위)
                    sum += (buttonClickDate.getTime()-userVisitDate.getTime())/1000;

                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        }

        return userFirstClickTime.size() <= 0 ? 0.0 :sum / userFirstClickTime.size();
    }
}
