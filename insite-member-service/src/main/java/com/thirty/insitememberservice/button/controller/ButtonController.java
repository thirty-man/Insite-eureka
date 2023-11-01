package com.thirty.insitememberservice.button.controller;

import com.thirty.insitememberservice.button.dto.response.ButtonCreateResDto;
import com.thirty.insitememberservice.button.dto.response.ButtonListResDto;
import com.thirty.insitememberservice.button.dto.request.ButtonCreateReqDto;
import com.thirty.insitememberservice.button.dto.request.ButtonDeleteReqDto;
import com.thirty.insitememberservice.button.dto.request.ButtonModifyReqDto;
import com.thirty.insitememberservice.button.service.ButtonService;
import com.thirty.insitememberservice.global.config.auth.LoginUser;
import com.thirty.insitememberservice.global.config.jwt.JwtProcess;
import com.thirty.insitememberservice.global.config.jwt.JwtVO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/buttons")
public class ButtonController {

    private final ButtonService buttonService;

    @PostMapping
    public ResponseEntity<ButtonCreateResDto> buttonCreate(
        @Valid @RequestBody ButtonCreateReqDto buttonCreateReqDto,
        HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        ButtonCreateResDto buttonCreateResDto = buttonService.create(loginUser.getMember().getMemberId(), buttonCreateReqDto);
        return new ResponseEntity<>(buttonCreateResDto, HttpStatus.OK);
    }

    @PatchMapping("/{buttonId}/remove")
    public ResponseEntity<Void> buttonDelete(
        @Valid @RequestBody ButtonDeleteReqDto buttonDeleteReqDto,
        @Valid @PathVariable int buttonId,
        HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        buttonService.delete(loginUser.getMember().getMemberId(), buttonId, buttonDeleteReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{buttonId}")
    public ResponseEntity<Void> buttonModify(
        @Valid @RequestBody ButtonModifyReqDto buttonModifyReqDto,
        @Valid @PathVariable int buttonId,
        HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        buttonService.modify(loginUser.getMember().getMemberId(), buttonId, buttonModifyReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ButtonListResDto> getMyButtonList(
        @Valid @RequestParam("applicationId") int applicationId,
        @Valid @RequestParam("page") int page,
        HttpServletRequest request
    ){
        String token = request.getHeader(JwtVO.HEADER).replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verifyAccessToken(token);//검증
        ButtonListResDto buttonListResDto = buttonService.getMyButtonList(loginUser.getMember().getMemberId(), applicationId, page);
        return new ResponseEntity<>(buttonListResDto, HttpStatus.OK);
    }
}
