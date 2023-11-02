package com.thirty.insitewriteservice.write.service;

import com.thirty.insitewriteservice.global.service.InfluxDBService;
import com.thirty.insitewriteservice.write.dto.DataReqDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WriteServiceImpl implements WriteService {
    private final InfluxDBService influxDBService;
    @Override
    public void writeLongData(DataReqDto dataReqDto) {
        influxDBService.writeDataToData(dataReqDto);
    }

    @Override
    public void writeRealData(DataReqDto dataReqDto) {}
}

