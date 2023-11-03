package com.thirty.insitereadservice.cumulativedata.activeuser.controller;

import com.thirty.insitereadservice.cumulativedata.activeuser.dto.reqDto.*;
import com.thirty.insitereadservice.cumulativedata.activeuser.dto.resDto.*;
import com.thirty.insitereadservice.cumulativedata.activeuser.service.ActiveUserService;
import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.PageViewReqDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.UserCountReqDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.PageViewResDto;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.UserCountResDto;
import com.thirty.insitereadservice.cumulativedata.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@AllArgsConstructor
@RequestMapping("/active-user")
public class ActiveUserController {
    private final ActiveUserService activeUserService;
    private final UserService userService;

    @PostMapping ("/active-users-counts")
    public ResponseEntity<ActiveUserResDto> getActiveUserCounts(@Valid @RequestBody ActiveUserReqDto activeUserReqDto){
        ActiveUserResDto activeUserResDto = activeUserService.getActiveUserCount(activeUserReqDto);
        return new ResponseEntity<>(activeUserResDto, HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-active-user")
    public ResponseEntity<ViewCountsPerActiveUserResDto> getViewCountsPerActiveUser(@Valid @RequestBody ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto){
        PageViewResDto pageViewResDto =userService.getPageView(PageViewReqDto.builder().applicationToken(viewCountsPerActiveUserReqDto.getApplicationToken()).currentUrl(viewCountsPerActiveUserReqDto.getCurrentUrl()).build());
        ActiveUserResDto activeUserResDto = activeUserService.getActiveUserCount(ActiveUserReqDto.builder().applicationToken(viewCountsPerActiveUserReqDto.getApplicationToken()).build());
        ViewCountsPerActiveUserResDto viewCountsPerActiveUserResDto = ViewCountsPerActiveUserResDto.builder()
                .viewCountsPerActiveUser(pageViewResDto.getPageView()/ activeUserResDto.getActiveUserCount()).build();
        return new ResponseEntity<>(viewCountsPerActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-user")
    public ResponseEntity<ActiveUserPerUserResDto> getActiveUserPerUser(@Valid @RequestBody ActiveUserPerUserReqDto activeUserPerUserReqDto){
        ActiveUserResDto activeUserResDto = activeUserService.getActiveUserCount(ActiveUserReqDto.builder().applicationToken(activeUserPerUserReqDto.getApplicationToken()).build());
        UserCountResDto userCountResDto = userService.getUserCount(UserCountReqDto.builder().applicationToken(activeUserPerUserReqDto.getApplicationToken()).build());
        ActiveUserPerUserResDto activeUserPerUserResDto = ActiveUserPerUserResDto.builder().activeUserPerUser(activeUserResDto.getActiveUserCount()/ userCountResDto.getUserCount()).build();
        return new ResponseEntity<>(activeUserPerUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-os")
    public ResponseEntity<OsActiveUserResDto> getOsActiveUser(@Valid @RequestBody OsActiveUserReqDto osActiveUserReqDto){
        OsActiveUserResDto osActiveUserResDto=activeUserService.getOsActiveUserCounts(osActiveUserReqDto);
        return new ResponseEntity<>(osActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/average-active-time-per-active-user")
    public ResponseEntity<AverageActiveTimeResDto> getAverageActiveTime(@Valid @RequestBody AverageActiveTimeReqDto averageActiveTimeReqDto) throws ParseException {
        AverageActiveTimeResDto averageActiveTimeResDto = activeUserService.getAverageActiveTime(averageActiveTimeReqDto);
        return new ResponseEntity<>(averageActiveTimeResDto,HttpStatus.OK);
    }
}
