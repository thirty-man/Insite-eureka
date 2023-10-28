package com.thirty.insitememberservice.global.config.auth;


import com.thirty.insitememberservice.member.entity.Member;
import com.thirty.insitememberservice.member.repository.MemberRepository;
import com.thirty.insitememberservice.member.service.KakaoLoginService;
import com.thirty.insitememberservice.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
		String kakaoId = kakaoLoginService.createKakaoUser(accessToken);
		if (kakaoId.equals("") || kakaoId.equals(null)) {
			throw new InternalAuthenticationServiceException("로그인실패");
		}
		Optional<Member> userPS = memberRepository.findByKakaoIdAndGoodByeTimeIsNull(kakaoId);
		if (!userPS.isPresent()) {
			Member newMember = memberService.join(kakaoId);
			if (newMember == null) {
				throw new InternalAuthenticationServiceException("인증 및 가입 실패");
			}
			return new LoginUser(newMember);
		} else {
			return new LoginUser(userPS.get());
		}

	}

}