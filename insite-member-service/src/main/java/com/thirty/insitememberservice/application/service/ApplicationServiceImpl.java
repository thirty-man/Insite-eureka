package com.thirty.insitememberservice.application.service;

import com.thirty.insitememberservice.application.dto.ApplicationDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationCreateReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationDeleteReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationModifyReqDto;
import com.thirty.insitememberservice.application.dto.request.ApplicationTokenReqDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationCreateResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationResDto;
import com.thirty.insitememberservice.application.dto.response.ApplicationTokenResDto;
import com.thirty.insitememberservice.application.entity.Application;
import com.thirty.insitememberservice.application.repository.ApplicationRepository;
import com.thirty.insitememberservice.global.error.ErrorCode;
import com.thirty.insitememberservice.global.error.exception.ApplicationException;
import com.thirty.insitememberservice.global.error.exception.MemberException;
import com.thirty.insitememberservice.member.entity.Member;
import com.thirty.insitememberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService{

    private final MemberRepository memberRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationCreateResDto regist(ApplicationCreateReqDto applicationCreateReqDto, int memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId);
        if(optionalMember.isEmpty()){
            throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
        }
        Optional<Application> optionalApplication = applicationRepository.findApplicationByApplicationUrlAndIsDeletedIsFalse(applicationCreateReqDto.getApplicationUrl());
        if(optionalApplication.isPresent()){
            throw new ApplicationException(ErrorCode.ALREADY_EXIST_APPLICATION);
        }
        Member member= optionalMember.get();
        String token=UUID.randomUUID().toString();

        Application application = Application.create(
                member,
                applicationCreateReqDto.getName(),
                applicationCreateReqDto.getApplicationUrl(),
                token
        );
        System.out.println(application);

        applicationRepository.save(application);
        return ApplicationCreateResDto.from(application.getApplicationId());

    }

    @Override
    public void deleteApplication(ApplicationDeleteReqDto applicationDeleteReqDto, int memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId);

        if(optionalMember.isEmpty()){
            throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
        }

        Optional<Application> optionalApplication = applicationRepository.findApplicationByApplicationIdAndIsDeletedIsFalse(applicationDeleteReqDto.getApplicationId());
        if(optionalApplication.isEmpty()){
            throw new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION);
        }

        Member member= optionalMember.get();
        Application application= optionalApplication.get();

        if(!application.getMember().equals(member)){
            throw new MemberException(ErrorCode.NOT_OWNER_MEMBER);
        }

        application.delete();

    }

    @Override
    public void modifyApplication(ApplicationModifyReqDto applicationModifyReqDto, int memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId);
        if(optionalMember.isEmpty()){
            throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
        }
        Optional<Application> optionalApplication = applicationRepository.findApplicationByApplicationIdAndIsDeletedIsFalse(applicationModifyReqDto.getApplicationId());
        if(optionalApplication.isEmpty()){
            throw new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION);
        }

        Member member = optionalMember.get();
        Application application=optionalApplication.get();

        if(!application.getMember().equals(member)){
            throw new MemberException(ErrorCode.NOT_OWNER_MEMBER);
        }

        application.modify(applicationModifyReqDto.getName());

    }

    @Override
    public ApplicationTokenResDto getApplicationToken(ApplicationTokenReqDto applicationTokenReqDto, int memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId);
        if(optionalMember.isEmpty()) {
            throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
        }
        Optional<Application> optionalApplication = applicationRepository.findApplicationByApplicationIdAndIsDeletedIsFalse(applicationTokenReqDto.getApplicationId());
        if(optionalApplication.isEmpty()){
            throw new ApplicationException(ErrorCode.NOT_EXIST_APPLICATION);
        }
        Member member= optionalMember.get();
        Application application=optionalApplication.get();
        if(!application.getMember().equals(member)){
            throw new MemberException(ErrorCode.NOT_OWNER_MEMBER);
        }

        return ApplicationTokenResDto.from(application);
    }


    @Override
    public ApplicationResDto getMyApplicationList(int memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberIdAndGoodByeTimeIsNull(memberId);
        if(optionalMember.isEmpty()){
            throw new MemberException(ErrorCode.NOT_EXIST_MEMBER);
        }

        Member member=optionalMember.get();
        List<Application> applicationList = applicationRepository.findAllByMemberAndIsDeletedIsFalse(member);

        List<ApplicationDto> applicationDtoList = new ArrayList<>();
        for(Application application:applicationList){
            applicationDtoList.add(ApplicationDto.from(application));
        }
        return ApplicationResDto.from(applicationDtoList);
    }
}
