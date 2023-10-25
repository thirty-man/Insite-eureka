package com.thirty.ggulswriting.global.config.auth;

import com.thirty.ggulswriting.member.entity.Member;
import com.thirty.ggulswriting.member.repository.MemberRepository;
import com.thirty.ggulswriting.member.service.KakaoLoginService;
import com.thirty.ggulswriting.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        String accessToken = kakaoLoginService.getKakaoAccessToken(code);
        System.out.println("Login Service accessToken= "+accessToken);
        String kakaoId = kakaoLoginService.createKakaoUser(accessToken);//카카오 아이디와 닉네임을 같이 가지고 있음
        StringTokenizer st= new StringTokenizer(kakaoId);//카카오 아이디 분리
        String kakao = st.nextToken();//카카오 아이디만 가지고 있는 변수
        System.out.println("Login Service kakaoId ="+kakaoId);
        if(kakaoId.equals("")||kakaoId.equals(null)){
            System.out.println("Login Service kakaoId null");
            throw new InternalAuthenticationServiceException("로그인 실패");
        }

        Optional<Member> userPS = memberRepository.findByKakaoIdAndGoodbyeTimeIsNull(kakao); //카카오 아이디만 확인
        System.out.println("Login Service userPS 설정");
        if(!userPS.isPresent()){
            System.out.println("Login Service newMemeber1");
            Member newMember = memberService.join(kakaoId);
            System.out.println("Login Service newMemeber2");
            if(newMember==null){
                System.out.println("Login Service newMemeber 부재");
                throw new InternalAuthenticationServiceException("인증 및 가입 실패");
            }
            
            return new LoginUser(newMember);
        }else{
            System.out.println("Login Service userPS 존재");
            return new LoginUser(userPS.get());
        }
    }
}
