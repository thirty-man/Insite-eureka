package com.thirty.insiterealtimereadservice.global.builder;

import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class InfluxQueryBuilder {

    @Value("${influxdb.bucket}")
    private String bucket;

    private Instant getNow() {
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }
    private Instant getBeforeThirtyMinutes() {
        return LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.UTC);
    }

    public Flux buttonClickCountPerUser(String applicationToken){
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );

        Flux query = Flux.from(bucket)
            .range( -30L, ChronoUnit.MINUTES)
            .filter(restrictions)
            .groupBy(new String[]{"name","cookieId"})
            .count();

        log.info("query={}",query);
        return query;
    }

    public String buttonAbnormal(String applicationToken){

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from(bucket: \"").append(bucket).append("\")\n");
        queryBuilder.append("  |> range(start: ").append(getBeforeThirtyMinutes()).append(", stop:").append(getNow()).append(")\n");
        queryBuilder.append("  |> filter(fn: (r) => r._measurement == \"button\" and r.applicationToken == \"")
            .append(applicationToken).append("\" and float(v: r.requestCnt) >= 10)\n");
        queryBuilder.append("  |> group(columns:[\"\"])\n");
        queryBuilder.append("  |> sort(columns: [\"_time\"])");

        log.info("query={}",queryBuilder);
        return queryBuilder.toString();
    }

    public Flux getCommonInfo(String applicationToken){
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(getBeforeThirtyMinutes(), getNow())
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);
        return query;
    }

    public Flux getReferrer(String applicationToken){

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(getBeforeThirtyMinutes(), getNow())
            .filter(restrictions)
            .groupBy("referrer")
            .count();

        log.info("query= {}",query);

        return query;
    }

    public Flux getUserCount(String applicationToken){

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(applicationToken)
        );
        Flux query = Flux.from(bucket)
            .range(getBeforeThirtyMinutes(), getNow())
            .filter(restrictions)
            .groupBy("currentUrl");

        log.info("query= {}",query);
        return query;
    }

    public String getDataAbnormal(String applicationToken){
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from(bucket: \"").append(bucket).append("\")\n");
        queryBuilder.append("  |> range(start: ").append(getBeforeThirtyMinutes()).append(", stop:").append(getNow()).append(")\n");
        queryBuilder.append("  |> filter(fn: (r) => r._measurement == \"data\" and r.applicationToken == \"")
            .append(applicationToken).append("\" and float(v: r.requestCnt) >= 10)\n");
        queryBuilder.append("  |> group(columns:[\"\"])\n");
        queryBuilder.append("  |> sort(columns: [\"_time\"])");

        log.info("query={}",queryBuilder);
        return queryBuilder.toString();
    }
}
