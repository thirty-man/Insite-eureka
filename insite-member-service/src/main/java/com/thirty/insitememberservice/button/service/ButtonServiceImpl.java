package com.thirty.insitememberservice.button.service;

import com.thirty.insitememberservice.application.entity.Application;
import com.thirty.insitememberservice.application.repository.ApplicationRepository;
import com.thirty.insitememberservice.button.dto.ButtonDto;
import com.thirty.insitememberservice.button.dto.request.ButtonCreateReqDto;
import com.thirty.insitememberservice.button.dto.request.ButtonDeleteReqDto;
import com.thirty.insitememberservice.button.dto.request.ButtonModifyReqDto;
import com.thirty.insitememberservice.button.dto.response.ButtonCreateResDto;
import com.thirty.insitememberservice.button.dto.response.ButtonListResDto;
import com.thirty.insitememberservice.button.entity.Button;
import com.thirty.insitememberservice.button.repository.ButtonRepository;
import com.thirty.insitememberservice.global.error.ErrorCode;
import com.thirty.insitememberservice.global.error.exception.ApplicationException;
import com.thirty.insitememberservice.global.error.exception.ButtonException;
import com.thirty.insitememberservice.global.error.exception.MemberException;
import com.thirty.insitememberservice.member.entity.Member;
import com.thirty.insitememberservice.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService{

    private final ButtonRepository buttonRepository;
    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;


    @Override
    public ButtonCreateResDto create(int memberId, ButtonCreateReqDto buttonCreateReqDto) {
        //멤버 검증
        Member member = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId)
            .orElseThrow(()-> new MemberException(ErrorCode.NOT_EXIST_MEMBER));

        //어플 검증
        Application application = applicationRepository.findApplicationByApplicationIdAndMemberAndIsDeletedIsFalse(buttonCreateReqDto.getApplicationId(),member)
            .orElseThrow(()-> new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION));

        //같은 이름 버튼 생성 불가
        Optional<Button> optionalButton = buttonRepository.findByApplicationAndNameAndIsDeletedIsFalse(application, buttonCreateReqDto.getName());
        if(optionalButton.isPresent()){
            throw new ButtonException(ErrorCode.ALREADY_EXIST_BUTTON_NAME);
        }

        //버튼 생성
        Button button = Button.create(application, buttonCreateReqDto.getName());
        buttonRepository.save(button);

        return ButtonCreateResDto.create(button.getButtonId());
    }

    @Override
    public void delete(int memberId, int buttonId, ButtonDeleteReqDto buttonDeleteReqDto) {
        //멤버 검증
        Member member = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId)
            .orElseThrow(()-> new MemberException(ErrorCode.NOT_EXIST_MEMBER));

        //어플 검증
        Application application = applicationRepository.findApplicationByApplicationIdAndMemberAndIsDeletedIsFalse(buttonDeleteReqDto.getApplicationId(), member)
            .orElseThrow(()-> new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION));

        //버튼 검증
        Button button = buttonRepository.findByButtonIdAndApplicationAndIsDeletedIsFalse(buttonId, application)
            .orElseThrow(()-> new ButtonException(ErrorCode.NOT_EXIST_BUTTON));

        button.delete();
    }

    @Override
    public void modify(int memberId, int buttonId, ButtonModifyReqDto buttonModifyReqDto) {
        //멤버 검증
        Member member = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId)
            .orElseThrow(()-> new MemberException(ErrorCode.NOT_EXIST_MEMBER));

        //어플 검증
        Application application = applicationRepository.findApplicationByApplicationIdAndMemberAndIsDeletedIsFalse(buttonModifyReqDto.getApplicationId(), member)
            .orElseThrow(()-> new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION));

        //버튼 검증
        Button button = buttonRepository.findByButtonIdAndApplicationAndIsDeletedIsFalse(buttonId, application)
            .orElseThrow(()-> new ButtonException(ErrorCode.NOT_EXIST_BUTTON));

        button.modify(buttonModifyReqDto.getName());
    }

    @Override
    public ButtonListResDto getMyButtonList(int memberId, int applicationId, int page) {
        //멤버 검증
        Member member = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId)
            .orElseThrow(()-> new MemberException(ErrorCode.NOT_EXIST_MEMBER));

        //어플 검증
        Application application = applicationRepository.findApplicationByApplicationIdAndMemberAndIsDeletedIsFalse(applicationId, member)
            .orElseThrow(()-> new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION));

        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Order.asc("buttonId")));

        //버튼 리스트
        Page<Button> buttonList = buttonRepository.findAllByApplicationAndIsDeletedIsFalse(application, pageable);
        List<ButtonDto> buttonDtoList = new ArrayList<>();

        for(Button button: buttonList){
            buttonDtoList.add(ButtonDto.create(button.getButtonId(), button.getName()));
        }

        int totalPages = buttonList.getTotalPages();
        int currentPage = buttonList.getNumber();
        Boolean hasNext = buttonList.hasNext();

        return ButtonListResDto.create(buttonDtoList,totalPages, currentPage, hasNext);
    }
}
