package com.thirty.insitereadservice.activeusers.controller;

import com.thirty.insitereadservice.activeusers.dto.request.*;
import com.thirty.insitereadservice.activeusers.dto.response.*;
import com.thirty.insitereadservice.activeusers.service.ActiveusersService;
import com.thirty.insitereadservice.users.dto.request.TotalUserCountReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.UserCountDto;
import com.thirty.insitereadservice.users.dto.response.TotalUserCountResDto;
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
    @PostMapping("/active-users-per-currenturl") // 전체 활동 사용자 수 조회
    public ResponseEntity<ActiveUserResDto> getActiveUser(@Valid @RequestBody ActiveUserReqDto activeUserReqDto,HttpServletRequest request){
        //        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ActiveUserResDto activeUserResDto = activeusersService.getActiveUser(activeUserReqDto,memberId);
        return new ResponseEntity<>(activeUserResDto,HttpStatus.OK);
    }

    @PostMapping ("/active-users-counts") // currentUrl 별 활동 사용자 수 및 비율 조회
    public ResponseEntity<ActiveUserCountResDto> getActiveUserCounts(@Valid @RequestBody ActiveUserCountReqDto activeUserCountReqDto,
                                                             HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        ActiveUserCountResDto activeUserCountResDto = activeusersService.getActiveUserCount(activeUserCountReqDto,memberId);
        return new ResponseEntity<>(activeUserCountResDto, HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-active-user") 
    public ResponseEntity<ViewCountsPerActiveUserResDto> getViewCountsPerActiveUser(@Valid @RequestBody ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto,
                                                                                 HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;

        ViewCountsPerActiveUserResDto viewCountsPerActiveUserResDto = activeusersService.getViewCounts(viewCountsPerActiveUserReqDto,memberId);


        return new ResponseEntity<>(viewCountsPerActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-user") //전체 사용자 수 대비 활동 사용자 수 비율
    public ResponseEntity<ActiveUserPerUserResDto> getActiveUserPerUser(@Valid @RequestBody ActiveUserPerUserReqDto activeUserPerUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;


        TotalUserCountResDto totalUserCountResDto = usersService.getTotalUserCount(TotalUserCountReqDto.builder().endDateTime(activeUserPerUserReqDto.getEndDateTime())
                .startDateTime(activeUserPerUserReqDto.getStartDateTime()).applicationToken(activeUserPerUserReqDto.getApplicationToken()).build(),memberId);
        ActiveUserCountResDto activeUserCountResDto= activeusersService.getActiveUserCount(ActiveUserCountReqDto.builder()
                .applicationToken(activeUserPerUserReqDto.getApplicationToken()).startDateTime(activeUserPerUserReqDto.getStartDateTime())
                .endDateTime(activeUserPerUserReqDto.getEndDateTime()).build(),memberId);
        ActiveUserPerUserResDto activeUserPerUserResDto = ActiveUserPerUserResDto.builder().activeUserPerUser(activeUserCountResDto.getActiveUserCount()/ totalUserCountResDto.getTotal()).build();

        return new ResponseEntity<>(activeUserPerUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-os") //OS 별 활동 사용자 수
    public ResponseEntity<OsActiveUserResDto> getOsActiveUser(@Valid @RequestBody OsActiveUserReqDto osActiveUserReqDto,
        HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        OsActiveUserResDto osActiveUserResDto=activeusersService.getOsActiveUserCounts(osActiveUserReqDto,memberId);
        return new ResponseEntity<>(osActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/average-active-time-per-active-user") // 평균 활동 시간
    public ResponseEntity<AverageActiveTimeResDto> getAverageActiveTime(@Valid @RequestBody AverageActiveTimeReqDto averageActiveTimeReqDto,
        HttpServletRequest request) throws ParseException {
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        AverageActiveTimeResDto averageActiveTimeResDto = activeusersService.getAverageActiveTime(averageActiveTimeReqDto,memberId);
        return new ResponseEntity<>(averageActiveTimeResDto,HttpStatus.OK);
    }
}
