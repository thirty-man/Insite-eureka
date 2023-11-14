package com.thirty.insitereadservice.users.controller;

import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.request.ViewCountsPerUserReqDto;
import com.thirty.insitereadservice.users.dto.response.CookieIdUrlResDto;
import com.thirty.insitereadservice.users.dto.response.PageViewResDto;
import com.thirty.insitereadservice.users.dto.response.UserCountResDto;
import com.thirty.insitereadservice.users.dto.ViewCountsPerUserDto;
import com.thirty.insitereadservice.users.dto.request.AbnormalHistoryReqDto;
import com.thirty.insitereadservice.users.dto.response.AbnormalHistoryResDto;
import com.thirty.insitereadservice.users.service.UsersService;
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
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/abnormality")
    public ResponseEntity<AbnormalHistoryResDto> getAbnormalHistory(
        @Valid @RequestBody AbnormalHistoryReqDto abnormalHistoryReqDto,
        HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        AbnormalHistoryResDto exitFlowResDto = usersService.getAbnormalHistory(abnormalHistoryReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }

    @PostMapping("/view-counts") //모든 페이지의 조회수를 리스트로 담아 보내줍니다.
    public ResponseEntity<PageViewResDto> getPageView(@Valid @RequestBody PageViewReqDto pageViewReqDto,
                                                          HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        PageViewResDto pageViewResDto= usersService.getPageView(pageViewReqDto,memberId);
        return new ResponseEntity<>(pageViewResDto, HttpStatus.OK);
    }

    @PostMapping("/user-counts")//모든 페이지의 방문 유저수를 리스트로 담아 보내줍니다.
    public ResponseEntity<UserCountResDto> getUserCounts(@Valid @RequestBody UserCountReqDto userCountReqDto,
                                                             HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        UserCountResDto userCountResDto = usersService.getUserCount(userCountReqDto,memberId);
        return new ResponseEntity<>(userCountResDto,HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-user")//cookieId 별 각 페이지의 방문 횟수를 비율과 함께 리스트로 보내줍니다. cookieId리스트 안의 currentUrl리스트
    public ResponseEntity<CookieIdUrlResDto> getViewCountsPerUser(@Valid @RequestBody ViewCountsPerUserReqDto viewCountsPerUserReqDto,
                                                                  HttpServletRequest request
    ){
        int memberId = JwtProcess.verifyAccessToken(request);//검증

        CookieIdUrlResDto cookieIdUrlResDto= usersService.getCookieIdUrlCount(viewCountsPerUserReqDto,memberId);
        return new ResponseEntity<>(cookieIdUrlResDto,HttpStatus.OK);
    }
}
