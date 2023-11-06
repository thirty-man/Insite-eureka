package com.thirty.insitereadservice.flow.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insitereadservice.flow.dto.ExitFlowDto;
import com.thirty.insitereadservice.flow.dto.request.ExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;
import feign.FeignException;
import java.util.ArrayList;
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
public class FlowServiceImpl implements FlowService {

    private final MemberServiceClient memberServiceClient;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto, int memberId) {
        String token = exitFlowReqDto.getApplicationToken();
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
            .groupBy("activityId")
            .pivot(new String[]{"_value"}, new String[]{"_field"},"currentUrl");

        log.info("query = {}" ,query);


        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> exitPageRank = new HashMap<>();
        double totalUser = tables.size();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record: records) {
                String exitPageUrl = record.getValueByKey("applicationUrl").toString();

                if(exitPageRank.containsKey(exitPageUrl)){
                    exitPageRank.put(exitPageUrl, exitPageRank.get(exitPageUrl)+1);
                }else{
                    exitPageRank.put(exitPageUrl, 1);
                }
            }
        }
        List<ExitFlowDto> exitFlowDtoList = new ArrayList<>();
        for(String exitPageUrl: exitPageRank.keySet()){
            int count = exitPageRank.get(exitPageUrl);
            exitFlowDtoList.add(ExitFlowDto.create(exitPageUrl, count,(totalUser == 0.0) ? 0.0:count/totalUser));
        }
        return ExitFlowResDto.create(exitFlowDtoList);
    }
}
