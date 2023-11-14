package com.thirty.insitereadservice.currenturl.service;

import com.thirty.insitereadservice.currenturl.dto.req.CurrentUrlListReqDto;
import com.thirty.insitereadservice.currenturl.dto.res.CurrentUrlListResDto;

public interface CurrentUrlService {
    CurrentUrlListResDto getCurrentUrlList(CurrentUrlListReqDto currentUrlListReqDto,int memberId);
}
