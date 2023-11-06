package com.thirty.insitewriteservice.longtime;

import com.thirty.insitewriteservice.write.dto.ButtonReqDto;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

public interface LongtimeService {

	void writeLongData(DataReqDto dataReqDto);

	void writeLongButton(ButtonReqDto buttonReqDto);
}
