package org.spring.dev.company.service.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.member.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final PasswordEncoder passwordEncoder;
    private final LoginRepository loginRepository;




    @Transactional
    public MemberDto loginUpdateOk(Long id) {
        // MemberEntity id 확인
        Optional<MemberEntity> optionalMemberEntity =
                Optional.ofNullable(loginRepository.findById(id).orElseThrow(() -> {
                    return new IllegalArgumentException("수정할 아이디가 없습니다.");
                }));

        MemberEntity memberEntity = optionalMemberEntity.get();

        if(optionalMemberEntity.isPresent()){

            MemberDto memberDto = MemberDto.builder()
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .birth(memberEntity.getBirth())
                    .email(memberEntity.getEmail())
                    .phone(memberEntity.getPhone())
                    .postcode(memberEntity.getPostcode())
                    .address(memberEntity.getAddress())
                    .detailAddress(memberEntity.getDetailAddress())
                    .extraAddress(memberEntity.getExtraAddress())
                    .grade(memberEntity.getGrade())
                    .gender(memberEntity.getGender())
                    .password(passwordEncoder.encode(memberEntity.getPassword()))
                    .build();
            return memberDto;
        }

        return null;
    }

    @Transactional
    public int loginUpdate(MemberDto memberDto) {
        Optional<MemberEntity>  optionalMemberEntity=
                Optional.ofNullable(loginRepository.findById(memberDto.getId()).orElseThrow(() -> {
                    return new IllegalArgumentException("수정할 아이디가 없습니다.");
                }));

        MemberEntity memberEntity = MemberEntity.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .birth(memberDto.getBirth())
                .email(memberDto.getEmail())
                .phone(memberDto.getPhone())
                .postcode(memberDto.getPostcode())
                .address(memberDto.getAddress())
                .detailAddress(memberDto.getDetailAddress())
                .extraAddress(memberDto.getExtraAddress())
                .grade(memberDto.getGrade())
                .gender(memberDto.getGender())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .build();


        Long memberId = loginRepository.save(memberEntity).getId();

        Optional<MemberEntity> optionalMemberEntity2=
                Optional.ofNullable(loginRepository.findById(memberId).orElseThrow(() -> {
                    return new IllegalArgumentException("수정 아이디가 없습니다.");
                }));

        if(optionalMemberEntity2.isPresent()){
            // 수정이 정상 실행
            return 1;
        }
        return 0;
    }
}
