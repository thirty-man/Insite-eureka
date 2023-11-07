package com.thirty.insitewriteservice.write.service;

import com.thirty.insitewriteservice.write.dto.ButtonReqDto;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

public interface WriteService {
    void writeData(DataReqDto dataReqDto);

    void writeButton(ButtonReqDto buttonReqDto);
}
