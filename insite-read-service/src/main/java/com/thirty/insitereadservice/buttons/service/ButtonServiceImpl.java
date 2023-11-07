package com.thirty.insitereadservice.buttons.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsPerActiveUserReqDto;
import com.thirty.insitereadservice.buttons.dto.request.ExitPercentageReqDto;
import com.thirty.insitereadservice.buttons.dto.request.FirstClickTimeReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsPerActiveUserResDto;
import com.thirty.insitereadservice.buttons.dto.response.ClickCountsResDto;
import com.thirty.insitereadservice.buttons.dto.ClickCountsDto;
import com.thirty.insitereadservice.buttons.dto.request.ClickCountsReqDto;
import com.thirty.insitereadservice.buttons.dto.response.ExitPercentageResDto;
import com.thirty.insitereadservice.buttons.dto.response.FirstClickTimeResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import feign.FeignException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService{
    private final MemberServiceClient memberServiceClient;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ClickCountsResDto getClickCounts(ClickCountsReqDto clickCountsReqDto, int memberId) {
        String token = clickCountsReqDto.getApplicationToken();
        try{
            memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));
        }catch (FeignException fe){
            log.error(fe.getMessage());
        }
        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("name").equal(clickCountsReqDto.getButtonName())
        );
        Flux query = Flux.from("insite")
            .range(0L)
            .filter(restrictions)
            .groupBy(new String[]{"_time", "name"})
            .count();

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> countsMap = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                String date = records.get(0).getValueByKey("_time").toString();
                String countStringValue = records.get(0).getValueByKey("_value").toString();
                int count = Integer.valueOf(countStringValue);

                // Map에 이름(name)과 해당 이름의 카운트 값을 저장
                countsMap.put(date, count);
            }
        }

        Map<String, Integer> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : countsMap.entrySet()) {
            String date = entry.getKey().split("T")[0];
            int value = entry.getValue();

            if (result.containsKey(date)) {
                result.put(date, result.get(date) + value);
            } else {
                result.put(date, value);
            }
        }

        //response 형식으로 변환
        List<ClickCountsDto> countDtoList = new ArrayList<>();
        for(String date : result.keySet()){
            countDtoList.add(ClickCountsDto.create(date, result.get(date)));
        }

        return ClickCountsResDto.create(countDtoList);
    }

    @Override
    public ClickCountsPerActiveUserResDto getClickCountsPerActiveUser(
        ClickCountsPerActiveUserReqDto clickCountsPerActiveUserReqDto,
        int memberId
    ) {
        String token = clickCountsPerActiveUserReqDto.getApplicationToken();

        try{
            memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));
        }catch (FeignException fe){
            log.error(fe.getMessage());
        }
        //전체 활동 사용자 수 조회 쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions activityRestrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );

        Flux activityQuery = Flux.from("insite")
            .range(0L)
            .filter(activityRestrictions)
            .groupBy("activityId");
        log.info("activityQuery = {}" ,activityQuery);

        List<FluxTable> activeTables = queryApi.query(activityQuery.toString());
        double activitySum = activeTables.size();
        if(activitySum == 0.0){
            return ClickCountsPerActiveUserResDto.create(0.0);
        }
        //버튼 누른 횟수 조회
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("name").equal(clickCountsPerActiveUserReqDto.getButtonName())
        );

        Flux query = Flux.from("insite")
            .range(0L)
            .filter(restrictions)
            .count();
        log.info("query = {}" ,query);
        List<FluxTable> tables = queryApi.query(query.toString());
        double buttonCLicks = 0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                String countStringValue = record.getValueByKey("_value").toString();
                double count = Double.valueOf(countStringValue);
                buttonCLicks += count;
            }
        }

        log.info("activitySum={}", activitySum);
        log.info("buttonCLicks={}",buttonCLicks);
        return ClickCountsPerActiveUserResDto.create(buttonCLicks/activitySum);
    }

    @Override
    public ExitPercentageResDto getExitPercentage(ExitPercentageReqDto exitCountsReqDto, int memberId) {
        String token = exitCountsReqDto.getApplicationToken();

        try{
            memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));
        }catch (FeignException fe){
            log.error(fe.getMessage());
        }

        //해당 버튼을 누른 전체 사용자 수
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("name").equal(exitCountsReqDto.getButtonName())
        );

        Flux query = Flux.from("insite")
            .range(0L)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_value"}, new String[]{"_field"}, "_time");

        log.info("query = {}" ,query);
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String,String> userButtonClickTime = new HashMap<>();
        double totalButtonClickUsers = tables.size();

        //전체 사용자가 없는경우 바로 0리턴
        if(totalButtonClickUsers == 0){
            return ExitPercentageResDto.create(0);
        }
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                String activityId = record.getValueByKey("activityId").toString();
                String timeStringValue = record.getValueByKey("applicationUrl").toString();

                userButtonClickTime.put(activityId, timeStringValue);
            }
        }

        //data에서 모든 activityId의 마지막 활동 시간을 조회한다
        // 위의 맵과 일치하는 아이디를 선별하여 비교하는데 button 테이블이 가장 마지막인경우 접속 종료자

        Restrictions dataRestrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux dataQuery = Flux.from("insite")
            .range(0L)
            .filter(dataRestrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_value"}, new String[]{"_field"}, "_time");

        log.info("dataQuery ={}", dataQuery);
        List<FluxTable> dataTables = queryApi.query(dataQuery.toString());
        Map<String, String> userLastTime = new HashMap<>();
        for (FluxTable fluxTable : dataTables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                String activityId = record.getValueByKey("activityId").toString();
                String timeStringValue = record.getValueByKey("applicationUrl").toString();

                userLastTime.put(activityId, timeStringValue);
            }
        }
        double exitUsersAfterButtonClick = calculateExitUsers(userButtonClickTime, userLastTime);

        return ExitPercentageResDto.create(exitUsersAfterButtonClick/totalButtonClickUsers);
    }

//    @Override
//    public FirstClickTimeResDto getFirstClickTimeAvg(FirstClickTimeReqDto firstClickTimeReqDto,
//        int memberId) {
//        String token = firstClickTimeReqDto.getApplicationToken();
//
//        try{
//            memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));
//        }catch (FeignException fe){
//            log.error(fe.getMessage());
//        }
//
//        //버튼에서 url을 알아오면서 activityId가 처음 해당 버튼을 누른 시간 즉 레코드에 0번째행만 검사
//        //data에서 같은 url이고 같은 activityId의 첫 이동 시간을 버튼 시간에서 뺀다 그렇게 나온거 다 더해서 버튼 map size로 나눈다
//        //기간에 button기록이 page기록보다 앞설경우 -가 나옴...
//        QueryApi queryApi = influxDBClient.getQueryApi();
//
//        Restrictions buttonRestrictions = Restrictions.and(
//            Restrictions.measurement().equal("button"),
//            Restrictions.tag("applicationToken").equal(token),
//            Restrictions.tag("name").equal(firstClickTimeReqDto.getButtonName())
//        );
//        Flux buttonQuery = Flux.from("insite")
//            .range(0L)
//            .filter(buttonRestrictions)
//            .groupBy("activityId");
////            .pivot(new String[]{"currentUrl"}, new String[]{"_field"}, "_time");
//
//        log.info("buttonQuery ={}", buttonQuery);
//        List<FluxTable> buttonTables = queryApi.query(buttonQuery.toString());
//
//        //해당 기간에 버튼을 누른 사람이 없는경우 0 리턴
//        if(buttonTables.size() == 0){
//            return FirstClickTimeResDto.create(0.0);
//        }
//
//        Map<String, String> userFirstClickTime = new HashMap<>();
//        String currentUrl ="";
//        for (FluxTable fluxTable : buttonTables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//
//            if (!records.isEmpty()) {
//                String activityId = records.get(0).getValueByKey("activityId").toString();
//                String timeStringValue = records.get(0).getValueByKey("_time").toString();
//                currentUrl = records.get(0).getValueByKey("currentUrl").toString();
//                userFirstClickTime.put(activityId, timeStringValue);
//            }
//        }
//
//        Restrictions dataRestrictions = Restrictions.and(
//            Restrictions.measurement().equal("data"),
//            Restrictions.tag("applicationToken").equal(token),
//            Restrictions.tag("currentUrl").equal(currentUrl)
//        );
//
//        Flux dataQuery = Flux.from("insite")
//            .range(0L)
//            .filter(dataRestrictions)
//            .groupBy("activityId");
//
//        List<FluxTable> dataTables = queryApi.query(dataQuery.toString());
//        Map<String, String> userFirstVisit = new HashMap<>();
//        for (FluxTable fluxTable : dataTables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//
//            if (!records.isEmpty()) {
//                String activityId = records.get(0).getValueByKey("activityId").toString();
//                String timeStringValue = records.get(0).getValueByKey("_time").toString();
//                userFirstVisit.put(activityId, timeStringValue);
//            }
//        }
//
//        double firstClickTimeAvg = calculateFirstClickTimeAvg(userFirstClickTime, userFirstVisit);
//        return FirstClickTimeResDto.create(firstClickTimeAvg);
//    }

    private double calculateExitUsers(Map<String,String> userButtonClickTime, Map<String, String> userLastTime){
        double count = 0;
        for(String activityId: userButtonClickTime.keySet()){
            if(userLastTime.containsKey(activityId)) {
                log.info("키 값={}", activityId);
                String buttonClickTime = userButtonClickTime.get(activityId);
                String userExitTime = userLastTime.get(activityId);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                Date buttonClickDate;
                Date userExitDate;
                try {
                    buttonClickDate = dateFormat.parse(buttonClickTime);
                    userExitDate = dateFormat.parse(userExitTime);

                    // 두 시간 간의 차이 구하기
                    long timeDifference = userExitDate.getTime() - buttonClickDate.getTime();

                    if (timeDifference < 0) {
                        //버튼을 마지막으로 누른사람
                        count++;
                    }
                }catch (Exception e){
                    log.error(e.getMessage());
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
