package com.thirty.insitereadservice.activeusers.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.activeusers.dto.ActiveTimeDto;
import com.thirty.insitereadservice.activeusers.dto.request.ActiveUsersPerTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUsersPerTimeResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import feign.FeignException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class ActiveusersServiceImpl implements ActiveusersService {

    private final MemberServiceClient memberServiceClient;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ActiveUsersPerTimeResDto getActiveUsersPerTime(ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto, int memberId) {
        String token = activeUsersPerTimeReqDto.getApplicationToken();
        try{
            memberServiceClient.validationMemberAndApplication(
                MemberValidReqDto.create(token,memberId));
        }catch (FeignException fe){
            log.error(fe.getMessage());
        }
        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from("insite")
            .range(0L)
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, ActiveTimeDto> activeUserWithActiveTimeMap = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                int size = records.size();
                String activityId = records.get(0).getValueByKey("activityId").toString();

                //날짜 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime startTime = LocalDateTime.parse(records.get(0).getValueByKey("_time").toString(), formatter);

                LocalDateTime endTime;

                if(size == 1){
                    endTime = startTime.plusMinutes(30L);
                }else{
                    endTime = LocalDateTime.parse(records.get(size-1).getValueByKey("_time").toString(), formatter).plusMinutes(30L);
                }

                activeUserWithActiveTimeMap.put(activityId, ActiveTimeDto.create(startTime,endTime));
            }
        }

        return ActiveUsersPerTimeResDto.calculate(activeUserWithActiveTimeMap);
    }
}
