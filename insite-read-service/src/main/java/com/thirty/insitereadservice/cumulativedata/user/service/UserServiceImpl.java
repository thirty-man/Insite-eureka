package com.thirty.insitereadservice.cumulativedata.user.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.PageViewReqDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.UserCountReqDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.PageViewResDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.UserCountResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

    private char[] token = "A8b0xLYTE1K8Xfmen0Fei7HzzWwTzazTHtKFJMR8zOn5iiA5H1xz4Ln-V42ClEg4c2XAlFdjsSZ7w-5JAV8S9Q==".toCharArray();

    @Resource
    private InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org);

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