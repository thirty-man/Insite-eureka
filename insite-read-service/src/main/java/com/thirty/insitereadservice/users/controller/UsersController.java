package com.thirty.insitereadservice.users.controller;

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
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        AbnormalHistoryResDto exitFlowResDto = usersService.getAbnormalHistory(abnormalHistoryReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }

    //
    @PostMapping("/view-counts")
    public ResponseEntity<PageViewResDto> getPageView(@Valid @RequestBody PageViewReqDto pageViewReqDto,
                                                          HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        PageViewResDto pageViewResDto= usersService.getPageView(pageViewReqDto,memberId);
        return new ResponseEntity<>(pageViewResDto, HttpStatus.OK);
    }

    @PostMapping("/user-counts")
    public ResponseEntity<UserCountResDto> getUserCounts(@Valid @RequestBody UserCountReqDto userCountReqDto,
                                                             HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        UserCountResDto userCountResDto = usersService.getUserCount(userCountReqDto,memberId);
        return new ResponseEntity<>(userCountResDto,HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-user")
    public ResponseEntity<CookieIdUrlResDto> getViewCountsPerUser(@Valid @RequestBody ViewCountsPerUserReqDto viewCountsPerUserReqDto,
                                                                  HttpServletRequest request
    ){
//        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
//        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        int memberId = 1;
        CookieIdUrlResDto cookieIdUrlResDto= usersService.getCookieIdUrlCount(viewCountsPerUserReqDto,memberId);

        return new ResponseEntity<>(cookieIdUrlResDto,HttpStatus.OK);
    }
}
