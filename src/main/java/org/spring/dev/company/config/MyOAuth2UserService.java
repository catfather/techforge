package org.spring.dev.company.config;

import org.spring.dev.company.entity.member.MemberEntity;

import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.ApproType;

import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    // Google사용자 정보를 받아서 DB에 저장
    // google -> email, 비밀번호는 임의로, role: Member -> sec_member08 저장


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 실제 필요한 정보
        ClientRegistration clientRegistration = userRequest.getClientRegistration(); // 사용자
        String clientId = clientRegistration.getClientId();
        String registrationId = clientRegistration.getRegistrationId();
        String clientName = clientRegistration.getClientName();

        System.out.println("==============");

        Map<String, Object> attributes = oAuth2User.getAttributes();

        for (String key : attributes.keySet()) {
            System.out.println(key + ": " + attributes.get(key));
        }

        return snsUserAccess(oAuth2User, registrationId, attributes);

    }

    private OAuth2User snsUserAccess(OAuth2User oAuth2User, String registrationId, Map<String, Object> attributes) {

        String email = "";
        String password = "dkarjsk";
        String nickName = "";
        String name = "";

        if (registrationId.equals("google")) {
            System.out.println("구글 정보");
            email = oAuth2User.getAttribute("email");
            nickName = oAuth2User.getAttribute("nickname");
            name = oAuth2User.getAttribute("name");


        } else if (registrationId.equals("naver")) {
            System.out.println("네이버 로그인");
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            email = (String) response.get("email");
//            nickName = (String) response.get("nickname"); // naver
//            name = oAuth2User.getAttribute("name");
            name = (String) response.get("name");
            nickName = "nickname";

            System.out.println((String) response.get("id"));
            System.out.println((String) response.get("nickname"));
            System.out.println((String) response.get("gender"));
            System.out.println((String) response.get("email"));
            System.out.println((String) response.get("name"));


        } else if (registrationId.equals("kakao")) {

            System.out.println("카카오 로그인");
            // JSON으로 받은 데이터 -> Map으로 변환
            Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

            System.out.println("response : " + response);
            System.out.println("response - email : " + response.get("email"));

            Map<String, Object> profile = (Map<String, Object>) response.get("profile");
            System.out.println("response - nickname : " + profile.get("nickname"));

            email = (String) response.get("email");
            name = (String) profile.get("nickname");

           /* email = oAuth2User.getAttribute("email");
            nickName = oAuth2User.getAttribute("nickname");*/


            nickName = "nickname";


        }


        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(email);
        if (optionalMemberEntity.isPresent()) {
            // 구글 로그인 정보가 있으면 // 그정보 -> UserDetail에 저장
            return new MyUserDetails(optionalMemberEntity.get()); // 등록된 사용자 인증
        }


        MemberEntity memberEntity = memberRepository.save(MemberEntity.toOauth(email, name, passwordEncoder.encode(password)));
        return new MyUserDetails(memberEntity, oAuth2User.getAttributes());


    }

}




