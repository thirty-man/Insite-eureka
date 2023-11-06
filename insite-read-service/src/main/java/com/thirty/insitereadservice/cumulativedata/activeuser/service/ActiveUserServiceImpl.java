package com.thirty.insitereadservice.cumulativedata.activeuser.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.ActiveUserReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.AverageActiveTimeReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.OsActiveUserReqDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.ActiveUserResDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.OsActiveUserDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.AverageActiveTimeResDto;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.OsActiveUserResDto;
import com.thirty.insitereadservice.cumulativedata.user.service.UserService;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class ActiveUserServiceImpl implements ActiveUserService{

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

    private char[] token = "A8b0xLYTE1K8Xfmen0Fei7HzzWwTzazTHtKFJMR8zOn5iiA5H1xz4Ln-V42ClEg4c2XAlFdjsSZ7w-5JAV8S9Q==".toCharArray();

    @Resource
    private InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org);

    private final UserService userService;
    @Override
    public ActiveUserResDto getActiveUserCount(ActiveUserReqDto activeUserReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUserReqDto.getApplicationToken(),memberId));
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal("\""+activeUserReqDto.getApplicationToken()+"\"")
        );
        Flux query = Flux.from(bucket)
                .range(0L)
                .filter(restrictions)
                .groupBy("activityId")
                .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
                .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int count=tables.size();


        return ActiveUserResDto.builder().activeUserCount(count).build();
    }

    @Override
    public AverageActiveTimeResDto getAverageActiveTime(AverageActiveTimeReqDto averageActiveTimeReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(averageActiveTimeReqDto.getApplicationToken(),memberId));
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal("\""+averageActiveTimeReqDto.getApplicationToken()+"\"")
        );
        Flux query = Flux.from(bucket)
                .range(0L)
                .filter(restrictions)
                .groupBy("activityId");
        System.out.println(query.toString());
        List<OsActiveUserDto> osActiveUserDtoList = new ArrayList<>();
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        double size = tables.size();
        double time=0;
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            if(records.size()<=1)
                continue;
            try {
                StringTokenizer st = new StringTokenizer(records.get(0).getValueByKey("_time").toString(),"T");
                String from ="";
                from+=st.nextToken();
                from+=" ";
                from+=st.nextToken();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
                Date fromDate = transFormat.parse(from);
                st = new StringTokenizer(records.get(records.size() - 1).getValueByKey("_time").toString(),"T");
                String to="";
                to+=st.nextToken();
                to+=" ";
                to+=st.nextToken();
                Date toDate = transFormat.parse(to);
                long sec =(toDate.getTime()-fromDate.getTime())/1000;
                time+=sec;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        AverageActiveTimeResDto averageActiveTimeResDto = AverageActiveTimeResDto.builder()
                .averageActiveTime(time/size).build();
        return averageActiveTimeResDto;
    }


    @Override
    public OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal("\""+osActiveUserReqDto.getApplicationToken()+"\"")
        );
        Flux query = Flux.from(bucket)
                .range(0L)
                .filter(restrictions)
                .groupBy("osId")
                .count();
        System.out.println(query.toString());
        List<OsActiveUserDto> osActiveUserDtoList = new ArrayList<>();
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        for(FluxTable fluxTable :tables){
            List<FluxRecord> records = fluxTable.getRecords();
            for(FluxRecord record:records){
                System.out.println(record.getValueByKey("osId"));
                System.out.println(record.getValueByKey("_value"));
                osActiveUserDtoList.add(OsActiveUserDto.builder().os(record.getValueByKey("osId").toString())
                        .count(Integer.parseInt(record.getValueByKey("_value").toString())).build());
            }
        }
        return OsActiveUserResDto.from(osActiveUserDtoList);
    }
}
