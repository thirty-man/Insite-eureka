package com.thirty.insitereadservice.users.controller;

import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.request.ViewCountsPerUserReqDto;
import com.thirty.insitereadservice.users.dto.response.PageViewResDto;
import com.thirty.insitereadservice.users.dto.response.UserCountResDto;
import com.thirty.insitereadservice.users.dto.response.ViewCountsPerUserResDto;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.global.jwt.JwtVO;
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
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        AbnormalHistoryResDto exitFlowResDto = usersService.getAbnormalHistory(abnormalHistoryReqDto, memberId);
        return new ResponseEntity<>(exitFlowResDto, HttpStatus.OK);
    }

    //
    @PostMapping("/view-counts")
    public ResponseEntity<PageViewResDto> getPageView(@Valid @RequestBody PageViewReqDto pageViewReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        PageViewResDto pageViewResDto= usersService.getPageView(pageViewReqDto,memberId);
        return new ResponseEntity<>(pageViewResDto, HttpStatus.OK);
    }

    @PostMapping("/user-counts")
    public ResponseEntity<UserCountResDto> getUserCounts(@Valid @RequestBody UserCountReqDto userCountReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        UserCountResDto userCountResDto = usersService.getUserCount(userCountReqDto,memberId);
        return new ResponseEntity<>(userCountResDto,HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-user")
    public ResponseEntity<ViewCountsPerUserResDto> getViewCountsPerUser(@Valid @RequestBody ViewCountsPerUserReqDto viewCountsPerUserReqDto,
        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증

        PageViewResDto pageViewResDto = usersService.getPageView(PageViewReqDto.builder().applicationToken(viewCountsPerUserReqDto.getApplicationToken())
            .currentUrl(viewCountsPerUserReqDto.getCurrentUrl()).build(),memberId);
        UserCountResDto userCountResDto = usersService.getUserCount(UserCountReqDto.builder().applicationToken(viewCountsPerUserReqDto.getApplicationToken()).build(),memberId);
        ViewCountsPerUserResDto viewCountsPerUserResDto = ViewCountsPerUserResDto.builder().viewCountsPerUser(pageViewResDto.getPageView()/ userCountResDto.getUserCount()).build();

        return new ResponseEntity<>(viewCountsPerUserResDto,HttpStatus.OK);
    }
}
