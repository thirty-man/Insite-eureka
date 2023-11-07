package com.thirty.insitereadservice.users.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.response.PageViewResDto;
import com.thirty.insitereadservice.users.dto.response.UserCountResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insitereadservice.users.dto.AbnormalDto;
import com.thirty.insitereadservice.users.dto.AbnormalUrlAndCountDto;
import com.thirty.insitereadservice.users.dto.request.AbnormalHistoryReqDto;
import com.thirty.insitereadservice.users.dto.response.AbnormalHistoryResDto;
import feign.FeignException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class UsersServiceImpl implements UsersService {

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public AbnormalHistoryResDto getAbnormalHistory(AbnormalHistoryReqDto abnormalHistoryReqDto, int memberId) {
        String token = abnormalHistoryReqDto.getApplicationToken();
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
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("requestCnt").greaterOrEqual("30")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("currentUrl");

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, AbnormalUrlAndCountDto> dateAndAbnormalDtoUrlAndCountMap = new HashMap<>();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record: records) {
                String date = record.getValueByKey("_time").toString().split("T")[0];
                String currentUrl = record.getValueByKey("currentUrl").toString();

                if(dateAndAbnormalDtoUrlAndCountMap.containsKey(date)){
                    AbnormalUrlAndCountDto abnormalUrlAndCountDto = dateAndAbnormalDtoUrlAndCountMap.get(date);
                    abnormalUrlAndCountDto.addCount();

                    dateAndAbnormalDtoUrlAndCountMap.put(date, abnormalUrlAndCountDto);
                }else{
                    dateAndAbnormalDtoUrlAndCountMap.put(date, AbnormalUrlAndCountDto.create(currentUrl,1));
                }
            }
        }
        List<AbnormalDto> abnormalDtoList = new ArrayList<>();
        for(String date: dateAndAbnormalDtoUrlAndCountMap.keySet()){
            AbnormalUrlAndCountDto abnormalUrlAndCountDto = dateAndAbnormalDtoUrlAndCountMap.get(date);

            abnormalDtoList.add(
                AbnormalDto.create(date, abnormalUrlAndCountDto.getUrl(), abnormalUrlAndCountDto.getCount()));
        }
        return AbnormalHistoryResDto.create(abnormalDtoList);
    }

    //
    @Override
    public PageViewResDto getPageView(PageViewReqDto pageViewReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(pageViewReqDto.getApplicationToken(), memberId));
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+pageViewReqDto.getApplicationToken()+"\""),
            Restrictions.tag("currentUrl").equal("\""+pageViewReqDto.getCurrentUrl()+"\"")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("applicationToken")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int count=0;
        System.out.println(tables.size());
        for (FluxTable fluxTable : tables) {

            List<FluxRecord> records = fluxTable.getRecords();
            System.out.println(records.size());
            count+= records.size();

        }
        PageViewResDto pageViewResDto = PageViewResDto.builder().pageView(count).build();

        return pageViewResDto;
    }

    @Override
    public UserCountResDto getUserCount(UserCountReqDto userCountReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(userCountReqDto.getApplicationToken(),memberId));
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+userCountReqDto.getApplicationToken()+"\"")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("cookieId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int count=tables.size();


        return UserCountResDto.builder().userCount(count).build();
    }

}
