package com.thirty.insitereadservice.cumulativedata.page.controller;

import com.thirty.insitereadservice.cumulativedata.page.dto.reqDto.PageViewReqDto;
import com.thirty.insitereadservice.cumulativedata.page.dto.resDto.PageViewResDto;
import com.thirty.insitereadservice.cumulativedata.page.service.PageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class PageController {
    private PageService pageService;

    @GetMapping("/view-counts")
    public ResponseEntity<PageViewResDto> getPageView(@Valid @RequestBody PageViewReqDto pageViewReqDto
                                                      ){
        PageViewResDto pageViewResDto=pageService.getPageView(pageViewReqDto);
        return new ResponseEntity<>(pageViewResDto, HttpStatus.OK);
    }
}
