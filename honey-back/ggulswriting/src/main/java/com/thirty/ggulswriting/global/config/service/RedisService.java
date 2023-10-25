package com.thirty.ggulswriting.global.config.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Autowired
    private RedisTemplate<String, String>redisTemplate;

    public String getValues(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void setValues(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,14, TimeUnit.DAYS);
    }

    public void expireValues(String key){
        redisTemplate.expire(key,0, TimeUnit.SECONDS);
    }
}
