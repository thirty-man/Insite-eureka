package com.thirty.insitereadservice.activeusers.controller;

import com.thirty.insitereadservice.activeusers.dto.request.*;
import com.thirty.insitereadservice.activeusers.dto.response.*;
import com.thirty.insitereadservice.activeusers.service.ActiveusersService;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
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

    @PostMapping("/active-users-per-time")
    public ResponseEntity<ActiveUsersPerTimeResDto> getActiveUsersPerTime(
        @Valid @RequestBody ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        ActiveUsersPerTimeResDto exitFlowResDto = activeusersService.getActiveUsersPerTime(activeUsersPerTimeReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }
    @PostMapping("/active-users-per-currenturl") //각 페이지의 활동사용자 수와 비율을 리스트로 반환합니다.
    public ResponseEntity<ActiveUserResDto> getActiveUser(
        @Valid @RequestBody ActiveUserReqDto activeUserReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        ActiveUserResDto activeUserResDto = activeusersService.getActiveUser(activeUserReqDto,memberId);
        return new ResponseEntity<>(activeUserResDto,HttpStatus.OK);
    }

    @PostMapping ("/active-users-counts") // 해당 서비스의 활동 사용자수를 반환합니다.
    public ResponseEntity<ActiveUserCountResDto> getActiveUserCounts(
        @Valid @RequestBody ActiveUserCountReqDto activeUserCountReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        ActiveUserCountResDto activeUserCountResDto = activeusersService.getActiveUserCount(activeUserCountReqDto,memberId);
        return new ResponseEntity<>(activeUserCountResDto, HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-active-user") //각 페이지의 활동사용자 수 별 조회수를 비율과 함께 리스트로 반환합니다.
    public ResponseEntity<ViewCountsPerActiveUserResDto> getViewCountsPerActiveUser(
        @Valid @RequestBody ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        ViewCountsPerActiveUserResDto viewCountsPerActiveUserResDto = activeusersService.getViewCounts(viewCountsPerActiveUserReqDto,memberId);
        return new ResponseEntity<>(viewCountsPerActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-user") //각 페이지 별 사용자 수 대비 활동 사용자 수와 비율을 리스트로 반환합니다.
    public ResponseEntity<ActiveUserPerUserResDto> getActiveUserPerUser(
        @Valid @RequestBody ActiveUserPerUserReqDto activeUserPerUserReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        ActiveUserPerUserResDto activeUserPerUserResDto=activeusersService.getActiveUserPerUser(activeUserPerUserReqDto,memberId);
        return new ResponseEntity<>(activeUserPerUserResDto,HttpStatus.OK);
    }

    @PostMapping("/active-user-per-os") //OS 별 활동 사용자 수를 리스트로 반환합니다.
    public ResponseEntity<OsActiveUserResDto> getOsActiveUser(
        @Valid @RequestBody OsActiveUserReqDto osActiveUserReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        OsActiveUserResDto osActiveUserResDto=activeusersService.getOsActiveUserCounts(osActiveUserReqDto,memberId);
        return new ResponseEntity<>(osActiveUserResDto,HttpStatus.OK);
    }

    @PostMapping("/average-active-time-per-active-user") //각 페이지별 활동 사용자의 평균 활동 시간을 리스트로 반환합니다.
    public ResponseEntity<AverageActiveTimeResDto> getAverageActiveTime(
        @Valid @RequestBody AverageActiveTimeReqDto averageActiveTimeReqDto,
        HttpServletRequest request
    ) throws ParseException {
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        AverageActiveTimeResDto averageActiveTimeResDto= activeusersService.getAverageActiveTime(averageActiveTimeReqDto,memberId);
        return new ResponseEntity<>(averageActiveTimeResDto,HttpStatus.OK);
    }
}
