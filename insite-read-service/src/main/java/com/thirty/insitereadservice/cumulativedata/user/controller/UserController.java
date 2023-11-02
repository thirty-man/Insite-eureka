package com.thirty.insitereadservice.cumulativedata.user.controller;

import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.*;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.*;
import com.thirty.insitereadservice.cumulativedata.user.service.UserService;
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
public class UserController {
    private UserService userService;

    @GetMapping("/view-counts")
    public ResponseEntity<PageViewResDto> getPageView(@Valid @RequestBody PageViewReqDto pageViewReqDto
                                                      ){
        PageViewResDto pageViewResDto= userService.getPageView(pageViewReqDto);
        return new ResponseEntity<>(pageViewResDto, HttpStatus.OK);
    }

    @GetMapping("/user-counts")
    public ResponseEntity<UserCountResDto> getUserCounts(@Valid @RequestBody UserCountReqDto userCountReqDto){
        UserCountResDto userCountResDto = userService.getUserCount(userCountReqDto);
        return new ResponseEntity<>(userCountResDto,HttpStatus.OK);
    }

    @GetMapping("/view-counts-per-user")
    public ResponseEntity<ViewCountsPerUserResDto> getViewCountsPerUser(@Valid @RequestBody ViewCountsPerUserReqDto viewCountsPerUserReqDto){
        PageViewResDto pageViewResDto = userService.getPageView(PageViewReqDto.builder().applicationToken(viewCountsPerUserReqDto.getApplicationToken())
                .currentUrl(viewCountsPerUserReqDto.getCurrentUrl()).build());
        UserCountResDto userCountResDto = userService.getUserCount(UserCountReqDto.builder().applicationToken(viewCountsPerUserReqDto.getApplicationToken()).build());
        ViewCountsPerUserResDto viewCountsPerUserResDto = ViewCountsPerUserResDto.builder().viewCountsPerUser(pageViewResDto.getPageView()/ userCountResDto.getUserCount()).build();

        return new ResponseEntity<>(viewCountsPerUserResDto,HttpStatus.OK);
    }


}
