package com.thirty.insitewriteservice.write.service;

import com.thirty.insitewriteservice.write.dto.DataReqDto;

public interface WriteService {
    void writeLongData(DataReqDto dataReqDto);

    void writeRealData(DataReqDto dataReqDto);
}
