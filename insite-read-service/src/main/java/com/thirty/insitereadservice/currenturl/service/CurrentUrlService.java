package com.thirty.insitereadservice.currenturl.service;

import com.thirty.insitereadservice.currenturl.dto.req.CurrentUrlListReqDto;
import com.thirty.insitereadservice.currenturl.dto.res.CurrentrUrlListResDto;

public interface CurrentUrlService {
    CurrentrUrlListResDto getCurrentUrlList(CurrentUrlListReqDto currentUrlListReqDto,int memberId);
}
