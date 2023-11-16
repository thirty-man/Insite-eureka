package com.thirty.insitereadservice.activeusers.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.thirty.insitereadservice.activeusers.builder.ActiveUsersQueryBuilder;
import com.thirty.insitereadservice.activeusers.dto.*;
import com.thirty.insitereadservice.activeusers.dto.request.*;
import com.thirty.insitereadservice.activeusers.dto.response.*;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveusersServiceImpl implements ActiveusersService {

    private final MemberServiceClient memberServiceClient;
    private final ActiveUsersQueryBuilder activeUsersQueryBuilder;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ActiveUsersPerTimeResDto getActiveUsersPerTime(ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUsersPerTimeReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();

        Flux query = activeUsersQueryBuilder.getActiveUserPerTime(
            activeUsersPerTimeReqDto.getStartDateTime(),
            activeUsersPerTimeReqDto.getEndDateTime(),
            activeUsersPerTimeReqDto.getApplicationToken());

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        return ActiveUsersPerTimeResDto.calculate(getStringActiveTimeDtoMap(tables));
    }

    @Override
    public ActiveUserResDto getActiveUser(ActiveUserReqDto activeUserReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUserReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();

        Flux query = activeUsersQueryBuilder.getActiveUser(
            activeUserReqDto.getStartDateTime(),
            activeUserReqDto.getEndDateTime(),
            activeUserReqDto.getApplicationToken()
        );

        List<FluxTable> tables = queryApi.query(query.toString());
        return ActiveUserResDto.create(getActiveUserDtoList(tables));
    }

    @Override
    public AverageActiveTimeResDto getAverageActiveTime(AverageActiveTimeReqDto averageActiveTimeReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(averageActiveTimeReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();

        String query = activeUsersQueryBuilder.getAverageActiveTime(
            averageActiveTimeReqDto.getStartDateTime(),
            averageActiveTimeReqDto.getEndDateTime(),
            averageActiveTimeReqDto.getApplicationToken()
        );

        List<FluxTable> tables = queryApi.query(query);
        return AverageActiveTimeResDto.create(getAverageActiveTimeDtoList(tables));
    }

    @Override
    public OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));
        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = activeUsersQueryBuilder.getOsActiveUserCounts(
            osActiveUserReqDto.getStartDateTime(),
            osActiveUserReqDto.getEndDateTime(),
            osActiveUserReqDto.getApplicationToken()
        );

        List<FluxTable> tables = queryApi.query(query.toString());
        return OsActiveUserResDto.from(getOsActiveUserDtoList(tables));
    }

    @Override
    public ActiveUserCountResDto getActiveUserCount(ActiveUserCountReqDto activeUserCountReqDto, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUserCountReqDto.getApplicationToken(),memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = activeUsersQueryBuilder.getActiveUserCount(
            activeUserCountReqDto.getStartDateTime(),
            activeUserCountReqDto.getEndDateTime(),
            activeUserCountReqDto.getApplicationToken()
        );

        List<FluxTable> tables = queryApi.query(query.toString());
        return ActiveUserCountResDto.create(tables.size());
    }

    @Override
    public ViewCountsPerActiveUserResDto getViewCounts(ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto, int memberId) {
         memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(viewCountsPerActiveUserReqDto.getApplicationToken(),memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux actQuery = activeUsersQueryBuilder.getActivityIdCounts(
            viewCountsPerActiveUserReqDto.getStartDateTime(),
            viewCountsPerActiveUserReqDto.getEndDateTime(),
            viewCountsPerActiveUserReqDto.getApplicationToken()
        );

        Flux query = activeUsersQueryBuilder.getViewCounts(
            viewCountsPerActiveUserReqDto.getStartDateTime(),
            viewCountsPerActiveUserReqDto.getEndDateTime(),
            viewCountsPerActiveUserReqDto.getApplicationToken()
        );

        List<FluxTable> actTables = queryApi.query(actQuery.toString());//currentUrl에 해당하는 activityId 갯수
        List<FluxTable> tables = queryApi.query(query.toString());//currentUrl에 해당하는 총 조회수

        HashMap<String, Integer> map = getCurrentUrlWithCount(actTables);//현재 Url의 활동 사용자 수
        return ViewCountsPerActiveUserResDto.create(getViewCountsPerActiveUserDtoList(map, tables));
    }

    @Override
    public ActiveUserPerUserResDto getActiveUserPerUser(ActiveUserPerUserReqDto activeUserPerUserReqDto, int memberId) {
         memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUserPerUserReqDto.getApplicationToken(),memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux actQuery = activeUsersQueryBuilder.getActivityIdCounts(
            activeUserPerUserReqDto.getStartDateTime(),
            activeUserPerUserReqDto.getEndDateTime(),
            activeUserPerUserReqDto.getApplicationToken()
        );

        Flux query = activeUsersQueryBuilder.getCurrentUrlUserCounts(
            activeUserPerUserReqDto.getStartDateTime(),
            activeUserPerUserReqDto.getEndDateTime(),
            activeUserPerUserReqDto.getApplicationToken()
        );

        List<FluxTable> tables = queryApi.query(actQuery.toString());//currentUrl에 따른 활동 사용자수
        HashMap<String, Integer> map = getCurrentUrlWithCount(tables);

        List<FluxTable> fluxTables = queryApi.query(query.toString());//currentUrl에 따른 사용자 수
        return ActiveUserPerUserResDto.create(getActiveUserPerUserDtoList(map, fluxTables));
    }

    @NotNull
    private Map<String, ActiveTimeDto> getStringActiveTimeDtoMap(List<FluxTable> tables) {
        Map<String, ActiveTimeDto> activeUserWithActiveTimeMap = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                int size = records.size();
                String activityId = records.get(0).getValueByKey("activityId").toString();

                //날짜 변환
                DateTimeFormatter millisecondFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String stringValueOfTime = records.get(0).getValueByKey("_time").toString();
                LocalDateTime startTime = LocalDateTime.parse(stringValueOfTime, stringValueOfTime.length() < 24 ? formatter : millisecondFormatter);

                LocalDateTime endTime;

                if(size == 1){
                    endTime = startTime.plusMinutes(30L);
                }else{
                    String stringValueOfEndTime = records.get(size-1).getValueByKey("_time").toString();
                    endTime = LocalDateTime.parse(stringValueOfEndTime, stringValueOfEndTime.length() < 24 ? formatter : millisecondFormatter).plusMinutes(30L);
                }

                activeUserWithActiveTimeMap.put(activityId, ActiveTimeDto.create(startTime,endTime));
            }
        }
        return activeUserWithActiveTimeMap;
    }

    @NotNull
    private List<ActiveUserDto> getActiveUserDtoList(List<FluxTable> tables) {
        PriorityQueue<ActiveUserDto> activeUserDtoPriorityQueue = new PriorityQueue<>();
        List<ActiveUserDto> activeUserDtoList = new ArrayList<>();

        int id=0;
        double size=0;

        for(FluxTable table: tables){
            List<FluxRecord> records=table.getRecords();
            size+=records.size();
            String currentUrl= records.get(0).getValueByKey("currentUrl").toString();
            activeUserDtoPriorityQueue.add(ActiveUserDto.create(currentUrl,records.size()));
        }
        while(!activeUserDtoPriorityQueue.isEmpty()){
            activeUserDtoList.add(activeUserDtoPriorityQueue.poll().add(id++,size));
        }
        return activeUserDtoList;
    }

    @NotNull
    private List<AverageActiveTimeDto> getAverageActiveTimeDtoList(List<FluxTable> tables) {
        HashMap<String,Double> map = new HashMap<>();
        HashMap<String,Integer> size = new HashMap<>();
        PriorityQueue<AverageActiveTimeDto> averageActiveTimeDtoPriorityQueue= new PriorityQueue<>();
        List<AverageActiveTimeDto> averageActiveTimeDtoList= new ArrayList<>();

        int id=0;

        for(FluxTable fluxTable: tables){//activityId에 따른 활동
            List<FluxRecord> records = fluxTable.getRecords();
            Date fromDate =Date.from(Instant.now());
            Date toDate = Date.from(Instant.now());
            if(records.size()<=1)
                continue;
            String before = records.get(0).getValueByKey("currentUrl").toString();
            StringTokenizer st = new StringTokenizer(records.get(0).getValueByKey("_time").toString(),"T");
            String from ="";
            from+=st.nextToken();
            from+=" ";
            from+=st.nextToken();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            try {
                fromDate = transFormat.parse(from);
            }catch (Exception e){
                log.error(e.getMessage());
            }
            for(FluxRecord record:records){
                String currentUrl = record.getValueByKey("currentUrl").toString();
                if(!currentUrl.equals(before)){
                    if(map.containsKey(currentUrl)) {
                        size.replace(currentUrl,size.get(currentUrl)+1);
                    }
                    else{
                        size.put(currentUrl,1);
                    }
                    st = new StringTokenizer(record.getValueByKey("_time").toString(),"T");
                    String to="";
                    to+=st.nextToken();
                    to+=" ";
                    to+=st.nextToken();

                    try {
                        toDate = transFormat.parse(to);
                    }catch (Exception e){
                        log.error(e.getMessage());
                    }
                    double sec =(toDate.getTime()-fromDate.getTime())/1000;
                    if(map.containsKey(currentUrl)){
                        map.replace(currentUrl,map.get(currentUrl)+sec);
                    }
                    else{
                        map.put(currentUrl,sec);
                    }
                    before=currentUrl;
                    fromDate=toDate;
                }
                else{
                    continue;
                }
            }
        }

        map.forEach((key,value)->{
            String current = key;
            double average = value/size.get(current);
            averageActiveTimeDtoPriorityQueue.add(AverageActiveTimeDto.create(current,average));

        });

        while(!averageActiveTimeDtoPriorityQueue.isEmpty()){
            averageActiveTimeDtoList.add(averageActiveTimeDtoPriorityQueue.poll().addId(id++));
        }
        return averageActiveTimeDtoList;
    }

    @NotNull
    private List<OsActiveUserDto> getOsActiveUserDtoList(List<FluxTable> tables) {
        List<OsActiveUserDto> osActiveUserDtoList = new ArrayList<>();
        PriorityQueue<OsActiveUserDto> osActiveUserDtoPriorityQueue = new PriorityQueue<>();

        int size=0;
        for(FluxTable fluxTable : tables){
            List<FluxRecord> records = fluxTable.getRecords();

            String os = records.get(0).getValueByKey("osId").toString();

            Set<String> activityIdSet = new HashSet<>();
            for(FluxRecord record:records){
                String activityId = record.getValueByKey("activityId").toString();
                activityIdSet.add(activityId);
            }
            size += activityIdSet.size();
            osActiveUserDtoPriorityQueue.offer(OsActiveUserDto.create(os, activityIdSet.size()));
        }

        int id = 0;
        while(!osActiveUserDtoPriorityQueue.isEmpty()){
            OsActiveUserDto osActiveUserDto = osActiveUserDtoPriorityQueue.poll();
            osActiveUserDtoList.add(osActiveUserDto.addId(id++).calculateRatio(osActiveUserDto.getCount()/(double)size));
        }
        return osActiveUserDtoList;
    }

    @NotNull
    private HashMap<String, Integer> getCurrentUrlWithCount(List<FluxTable> actTables) {
        HashMap<String, Integer> map = new HashMap<>();
        for (FluxTable table : actTables) {
            List<FluxRecord> records = table.getRecords();
            String currentUrl = records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            map.put(currentUrl, count);
        }
        return map;
    }

    @NotNull
    private List<ViewCountsPerActiveUserDto> getViewCountsPerActiveUserDtoList(
        HashMap<String, Integer> map, List<FluxTable> tables) {
        PriorityQueue<ViewCountsPerActiveUserDto> priorityQueue = new PriorityQueue<>();
        List<ViewCountsPerActiveUserDto> viewCountsPerActiveUserDtoList= new ArrayList<>();
        int id=0;
        for(FluxTable table: tables){
            List<FluxRecord> records= table.getRecords();
            String currentUrl= records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            int act= map.get(currentUrl);
            priorityQueue.add(ViewCountsPerActiveUserDto.create(currentUrl,count,act));

        }
        while(!priorityQueue.isEmpty()){
            viewCountsPerActiveUserDtoList.add(priorityQueue.poll().add(id++));
        }
        return viewCountsPerActiveUserDtoList;
    }

    @NotNull
    private List<ActiveUserPerUserDto> getActiveUserPerUserDtoList(HashMap<String, Integer> map,
        List<FluxTable> fluxTables) {
        HashMap<String, Integer> cookie = getCurrentUrlWithCount(fluxTables);
        PriorityQueue<ActiveUserPerUserDto> activeUserPerUserDtoPriorityQueue = new PriorityQueue<>();
        List<ActiveUserPerUserDto> activeUserPerUserDtoList=new ArrayList<>();
        int id=0;
        map.forEach((key,value)->{
            String currentUrl = key;
            int size= cookie.get(key);
            activeUserPerUserDtoPriorityQueue.add(ActiveUserPerUserDto.create(currentUrl,(double)value/(double) size));
        });
        while(!activeUserPerUserDtoPriorityQueue.isEmpty()){
            activeUserPerUserDtoList.add(activeUserPerUserDtoPriorityQueue.poll().addId(id++));
        }
        return activeUserPerUserDtoList;
    }
}
