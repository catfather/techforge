package org.spring.dev.config;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class TechForgeMockSecurityContext implements WithSecurityContextFactory<TechForgeMockUser> {

    private final MemberRepository memberRepository;


    @Override
    public SecurityContext createSecurityContext(TechForgeMockUser annotation) {
        MemberEntity member = MemberEntity.builder()
                .name(annotation.name())
                .birth(annotation.birth())
                .email(annotation.email())
                .nickName(annotation.nickName())
                .phone(annotation.phone())
                .password(annotation.password())
                .postcode(annotation.postcode())
                .address(annotation.address())
                .detailAddress(annotation.detailAddress())
                .extraAddress(annotation.extraAddress())
                .grade(annotation.grade())
                .gender(annotation.gender())
                .build();

        memberRepository.save(member);

        MyUserDetails principal = new MyUserDetails(member);

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_INTERN");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                member.getPassword(),
                List.of(role));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;

    }
}
