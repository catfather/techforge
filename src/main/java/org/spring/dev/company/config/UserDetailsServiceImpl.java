package org.spring.dev.company.config;

import lombok.AllArgsConstructor;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    // DB 테이블에 사용자 정보 == 입력받은(Form)정보를 비교 -> 일치하면 -> User, UserDetails

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("이메일 존재하지 않습니다."));

        return new MyUserDetails(memberEntity);
    }


}
