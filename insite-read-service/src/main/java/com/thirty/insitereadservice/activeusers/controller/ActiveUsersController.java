package com.thirty.insitereadservice.activeusers.controller;

import com.thirty.insitereadservice.activeusers.dto.request.ActiveUsersPerTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUsersPerTimeResDto;
import com.thirty.insitereadservice.activeusers.service.ActiveusersService;
import com.thirty.insitereadservice.activeusers.dto.request.ActiveUserPerUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.ActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.AverageActiveTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.OsActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.ViewCountsPerActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUserPerUserResDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUserResDto;
import com.thirty.insitereadservice.activeusers.dto.response.AverageActiveTimeResDto;
import com.thirty.insitereadservice.activeusers.dto.response.OsActiveUserResDto;
import com.thirty.insitereadservice.activeusers.dto.response.ViewCountsPerActiveUserResDto;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.global.jwt.JwtVO;
import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.response.PageViewResDto;
import com.thirty.insitereadservice.users.dto.response.UserCountResDto;
import com.thirty.insitereadservice.users.service.UsersService;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/active-users")
public class ActiveUsersController {

    private final ActiveusersService activeusersService;
    private final UsersService usersService;

    @PostMapping("/active-users-per-time")
    public ResponseEntity<ActiveUsersPerTimeResDto> getActiveUsersPerTime(
        @Valid @RequestBody ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ActiveUsersPerTimeResDto exitFlowResDto = activeusersService.getActiveUsersPerTime(activeUsersPerTimeReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }

    @PostMapping ("/active-users-counts")
    public ResponseEntity<ActiveUserResDto> getActiveUserCounts(@Valid @RequestBody ActiveUserReqDto activeUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ActiveUserResDto activeUserResDto = activeusersService.getActiveUserCount(activeUserReqDto,memberId);
        return new ResponseEntity<>(activeUserResDto, HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-active-user")
    public ResponseEntity<ViewCountsPerActiveUserResDto> getViewCountsPerActiveUser(@Valid @RequestBody ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        PageViewResDto pageViewResDto =usersService.getPageView(PageViewReqDto.builder().applicationToken(viewCountsPerActiveUserReqDto.getApplicationToken()).currentUrl(viewCountsPerActiveUserReqDto.getCurrentUrl()).build(),memberId);
        ActiveUserResDto activeUserResDto = activeusersService.getActiveUserCount(ActiveUserReqDto.builder().applicationToken(viewCountsPerActiveUserReqDto.getApplicationToken()).build(),memberId);

        ViewCountsPerActiveUserResDto viewCountsPerActiveUserResDto = ViewCountsPerActiveUserResDto.builder()
            .viewCountsPerActiveUser(pageViewResDto.getPageView()/ activeUserResDto.getActiveUserCount()).build();

        return new ResponseEntity<>(viewCountsPerActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-user")
    public ResponseEntity<ActiveUserPerUserResDto> getActiveUserPerUser(@Valid @RequestBody ActiveUserPerUserReqDto activeUserPerUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        ActiveUserResDto activeUserResDto = activeusersService.getActiveUserCount(ActiveUserReqDto.builder().applicationToken(activeUserPerUserReqDto.getApplicationToken()).build(),memberId);
        UserCountResDto userCountResDto = usersService.getUserCount(UserCountReqDto.builder().applicationToken(activeUserPerUserReqDto.getApplicationToken()).build(),memberId);

        ActiveUserPerUserResDto activeUserPerUserResDto = ActiveUserPerUserResDto.builder().activeUserPerUser(activeUserResDto.getActiveUserCount()/ userCountResDto.getUserCount()).build();

        return new ResponseEntity<>(activeUserPerUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-os")
    public ResponseEntity<OsActiveUserResDto> getOsActiveUser(@Valid @RequestBody OsActiveUserReqDto osActiveUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        OsActiveUserResDto osActiveUserResDto=activeusersService.getOsActiveUserCounts(osActiveUserReqDto,memberId);
        return new ResponseEntity<>(osActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/average-active-time-per-active-user")
    public ResponseEntity<AverageActiveTimeResDto> getAverageActiveTime(@Valid @RequestBody AverageActiveTimeReqDto averageActiveTimeReqDto,
        HttpServletRequest request) throws ParseException {
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        AverageActiveTimeResDto averageActiveTimeResDto = activeusersService.getAverageActiveTime(averageActiveTimeReqDto,memberId);
        return new ResponseEntity<>(averageActiveTimeResDto,HttpStatus.OK);
    }
}
