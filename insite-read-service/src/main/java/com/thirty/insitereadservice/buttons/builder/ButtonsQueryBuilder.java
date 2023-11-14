package com.thirty.insitereadservice.buttons.builder;

import com.influxdb.client.QueryApi;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ButtonsQueryBuilder {

    @Value("${influxdb.bucket}")
    private String bucket;

    private Instant[] getStartAndEndInstant(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Instant startInstant = startDateTime.plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = endDateTime.plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        return new Instant[] {startInstant, endInstant};
    }

    public Flux getClickCounts(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken, String buttonName){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        //쿼리 생성

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(applicationToken),
            Restrictions.tag("name").equal(buttonName)
        );
        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy(new String[]{"_time", "name"})
            .sort(new String[]{"_time"})
            .count();

        log.info("query = {}" ,query);
        return query;
    }

    //해당 버튼을 누른 전체 사용자 수
    public Flux getButtonClickActiveUsersDesc(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken, String buttonName){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(applicationToken),
            Restrictions.tag("name").equal(buttonName)
        );

        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("activityId")
            .sort(new String[]{"_time"}, true);

        log.info("query = {}" ,query);
        return query;
    }

    // data에서 모든 activityId의 마지막 활동 시간을 조회한다
    public Flux getLastActive(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("activityId")
            .sort(new String[]{"_time"});

        log.info("dataQuery ={}", query);
        return query;
    }

    public String getButtonAbnormal(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from(bucket: \"").append(bucket).append("\")\n");
        queryBuilder.append("  |> range(start: ").append(startAndEndInstant[0]).append(", stop:").append(startAndEndInstant[1]).append(")\n");
        queryBuilder.append("  |> filter(fn: (r) => r._measurement == \"button\" and r.applicationToken == \"")
            .append(applicationToken).append("\" and float(v: r.requestCnt) >= 10)\n");
        queryBuilder.append("  |> group(columns:[\"\"])\n");
        queryBuilder.append("  |> sort(columns: [\"_time\"])");


        log.info("query={}",queryBuilder);
        return queryBuilder.toString();
    }

    public Flux getEveryButtonClickCountsDesc(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions dataRestrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(dataRestrictions)
            .groupBy("name")
            .sort(new String[]{"_time"}, true);

        log.info("query ={}", query);
        return query;
    }
}
