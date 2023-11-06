package com.thirty.insitereadservice.cumulativedata.user.controller;

import com.thirty.insitereadservice.cumulativedata.user.dto.reqDto.*;
import com.thirty.insitereadservice.cumulativedata.user.dto.resDto.*;
import com.thirty.insitereadservice.cumulativedata.user.service.UserService;
import com.thirty.insitereadservice.global.jwt.JwtProcess;
import com.thirty.insitereadservice.global.jwt.JwtVO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @PostMapping("/view-counts")
    public ResponseEntity<PageViewResDto> getPageView(@Valid @RequestBody PageViewReqDto pageViewReqDto,
                                                      HttpServletRequest request
                                                      ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        PageViewResDto pageViewResDto= userService.getPageView(pageViewReqDto,memberId);
        return new ResponseEntity<>(pageViewResDto, HttpStatus.OK);
    }

    @PostMapping("/user-counts")
    public ResponseEntity<UserCountResDto> getUserCounts(@Valid @RequestBody UserCountReqDto userCountReqDto,
                                                         HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증
        UserCountResDto userCountResDto = userService.getUserCount(userCountReqDto,memberId);
        return new ResponseEntity<>(userCountResDto,HttpStatus.OK);
    }

    @PostMapping("/view-counts-per-user")
    public ResponseEntity<ViewCountsPerUserResDto> getViewCountsPerUser(@Valid @RequestBody ViewCountsPerUserReqDto viewCountsPerUserReqDto,
                                                                        HttpServletRequest request
    ){
        String jwtToken = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        int memberId = JwtProcess.verifyAccessToken(jwtToken);//검증

        PageViewResDto pageViewResDto = userService.getPageView(PageViewReqDto.builder().applicationToken(viewCountsPerUserReqDto.getApplicationToken())
                .currentUrl(viewCountsPerUserReqDto.getCurrentUrl()).build(),memberId);
        UserCountResDto userCountResDto = userService.getUserCount(UserCountReqDto.builder().applicationToken(viewCountsPerUserReqDto.getApplicationToken()).build(),memberId);
        ViewCountsPerUserResDto viewCountsPerUserResDto = ViewCountsPerUserResDto.builder().viewCountsPerUser(pageViewResDto.getPageView()/ userCountResDto.getUserCount()).build();

        return new ResponseEntity<>(viewCountsPerUserResDto,HttpStatus.OK);
    }


}
