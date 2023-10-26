package com.thirty.insite.global.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thirty.insite.member.entity.Member;
import com.thirty.insite.member.repository.MemberRepository;
import com.thirty.insite.member.service.KakaoLoginService;
import com.thirty.insite.member.service.MemberService;

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