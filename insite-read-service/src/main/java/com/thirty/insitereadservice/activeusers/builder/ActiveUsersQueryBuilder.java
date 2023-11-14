package com.thirty.insitereadservice.activeusers.builder;

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
public class ActiveUsersQueryBuilder {

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


    public Flux getActiveUserPerTime(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from("insite")
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);
        return query;
    }

    public Flux getActiveUser(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("currentUrl")
//            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .distinct("activityId")
            .sort(new String[] {"_time"},true)
            .yield();

        log.info("query ={}", query);
        return query;
    }

    public Flux getAverageActiveTime(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy(new String [] {"currentUrl","activityId"})
            .sort(new String[]{"_time"},true);

        log.info("query= {}", query);
        return query;
    }

    public Flux getOsActiveUserCounts(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );

        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("osId")
            .count();

        log.info("query= {}", query);

        return query;
    }

    public Flux getActiveUserCount(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );

        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("activityId")
            .count();

        log.info("query= {}", query);

        return query;
    }

    public Flux getActivityIdCounts(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );

        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("currentUrl")
            .distinct("activityId")
            .count();

        log.info("query= {}", query);
        return query;
    }

    public Flux getViewCounts(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );

        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0],startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("currentUrl")
            .count();

        log.info("query= {}", query);
        return query;
    }

    public Flux getCurrentUrlUserCounts(LocalDateTime startDateTime, LocalDateTime endDateTime, String applicationToken){
        Instant[] startAndEndInstant = getStartAndEndInstant(startDateTime,endDateTime);

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );

        Flux query = Flux.from(bucket)
            .range(startAndEndInstant[0], startAndEndInstant[1])
            .filter(restrictions)
            .groupBy("currentUrl")
            .distinct("cookieId")
            .sort(new String[]{"_time"},true)
            .count();

        log.info("query= {}", query);

        return query;
    }
}
