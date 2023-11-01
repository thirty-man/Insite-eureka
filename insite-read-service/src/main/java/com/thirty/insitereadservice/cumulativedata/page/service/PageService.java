package com.thirty.insitereadservice.cumulativedata.page.service;


import com.thirty.insitereadservice.cumulativedata.page.dto.reqDto.PageViewReqDto;
import com.thirty.insitereadservice.cumulativedata.page.dto.resDto.PageViewResDto;

public interface PageService {
    PageViewResDto getPageView(PageViewReqDto pageViewReqDto);

}
