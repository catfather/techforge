package org.spring.dev.company.service.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonMemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto detailMember(MyUserDetails myUserDetails) {
        Optional<MemberEntity> memberEntity =
                Optional.ofNullable(memberRepository.findById(myUserDetails.getMemberEntity().getId()).orElseThrow(IllegalArgumentException::new));

        return MemberDto.builder()
                .id(memberEntity.get().getId())
                .name(memberEntity.get().getName())
                .email(memberEntity.get().getEmail())
                .password(memberEntity.get().getPassword())
                .birth(memberEntity.get().getBirth())
                .phone(memberEntity.get().getPhone())
                .postcode(memberEntity.get().getPostcode())
                .businessNumber(memberEntity.get().getBusinessNumber())
                .career(memberEntity.get().getCareer())
                .companyName(memberEntity.get().getCompanyName())
                .address(memberEntity.get().getAddress())
                .detailAddress(memberEntity.get().getDetailAddress())
                .extraAddress(memberEntity.get().getExtraAddress())
                .gender(memberEntity.get().getGender())
                .grade(memberEntity.get().getGrade())
                .CreateTime(memberEntity.get().getCreateTime())
                .is_display(memberEntity.get().getIs_display())
                .build();
    }


    public MemberDto updateMember(MemberDto memberDto, MyUserDetails myUserDetails) {
        Optional<MemberEntity> memberEntity =
                Optional.ofNullable(memberRepository.findById(myUserDetails.getMemberEntity().getId()).orElseThrow(IllegalArgumentException::new));


        MemberEntity memberEntity1 = memberEntity.get();
        memberEntity1.setEmail(memberDto.getEmail());
        memberEntity1.setName(memberDto.getName());
        memberEntity1.setPhone(memberDto.getPhone());
        memberEntity1.setPostcode(memberDto.getPostcode());
        memberEntity1.setAddress(memberDto.getAddress());
        memberEntity1.setDetailAddress(memberDto.getDetailAddress());
        memberEntity1.setExtraAddress(memberDto.getExtraAddress());


        Long upId = memberRepository.save(memberEntity1).getId();

        if (upId != 0) {
            return MemberDto.builder()
                    .id(memberEntity1.getId())
                    .name(memberEntity1.getName())
                    .email(memberEntity1.getEmail())
                    .password(memberEntity1.getPassword())
                    .birth(memberEntity1.getBirth())
                    .phone(memberEntity1.getPhone())
                    .postcode(memberEntity1.getPostcode())
                    .address(memberEntity1.getAddress())
                    .detailAddress(memberEntity1.getDetailAddress())
                    .extraAddress(memberEntity1.getExtraAddress())
                    .gender(memberEntity1.getGender())
                    .grade(memberEntity1.getGrade())
                    .CreateTime(memberEntity1.getCreateTime())
                    .is_display(memberEntity1.getIs_display())
                    .build();
        }
        return MemberDto.builder()
                .id(memberEntity1.getId())
                .name(memberEntity1.getName())
                .email(memberEntity1.getEmail())
                .password(memberEntity1.getPassword())
                .birth(memberEntity1.getBirth())
                .phone(memberEntity1.getPhone())
                .postcode(memberEntity1.getPostcode())
                .address(memberEntity1.getAddress())
                .detailAddress(memberEntity1.getDetailAddress())
                .extraAddress(memberEntity1.getExtraAddress())
                .gender(memberEntity1.getGender())
                .grade(memberEntity1.getGrade())
                .CreateTime(memberEntity1.getCreateTime())
                .is_display(memberEntity1.getIs_display())
                .CreateTime(memberEntity1.getCreateTime())
                .UpdateTime(memberEntity1.getUpdateTime())
                .build();


    }

    public int disabledMember(MyUserDetails myUserDetails) {
        Optional<MemberEntity> memberEntity =
                Optional.ofNullable(memberRepository.findById(myUserDetails.getMemberEntity().getId()).orElseThrow(IllegalArgumentException::new));
        if (memberEntity.get().getIs_display() != 0) {

            MemberEntity memberEntity1 = memberEntity.get();
            memberEntity1.setIs_display(0);
            int rs = memberRepository.save(memberEntity1).getIs_display();
            return rs;
        }

        return 0;
    }

    public int passwordChange(MemberDto memberDto, MyUserDetails myUserDetails) {

        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(myUserDetails.getMemberEntity().getId()).orElseThrow(()->{
            return new IllegalArgumentException("아이디가 없습니다.");
        }));

        MemberEntity memberEntity = optionalMemberEntity.get();
        memberEntity.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        String password = memberRepository.save(memberEntity).getPassword();
        if (passwordEncoder.matches(memberDto.getPassword(), password)){
            return 1;
        }
        return 0;
    }

    public MemberDto freeUpdate(MemberDto memberDto, MyUserDetails myUserDetails) {
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(myUserDetails.getMemberEntity().getId()).orElseThrow(()->{
            return new IllegalArgumentException("아이디가 없습니다.");
        }));

        MemberEntity memberEntity = optionalMemberEntity.get();
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setName(memberDto.getName());
        memberEntity.setBirth(memberDto.getBirth());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setCareer(memberDto.getCareer());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setDetailAddress(memberDto.getDetailAddress());
        memberEntity.setExtraAddress(memberDto.getExtraAddress());

        memberRepository.save(memberEntity);
        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .birth(memberEntity.getBirth())
                .email(memberEntity.getEmail())
                .phone(memberEntity.getPhone())
                .postcode(memberEntity.getPostcode())
                .address(memberEntity.getAddress())
                .detailAddress(memberEntity.getDetailAddress())
                .extraAddress(memberEntity.getExtraAddress())
                .career(memberEntity.getCareer())
                .build();
    }


    public MemberDto companyUpdate(MemberDto memberDto, MyUserDetails myUserDetails) {
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(myUserDetails.getMemberEntity().getId()).orElseThrow(()->{
            return new IllegalArgumentException("아이디가 없습니다.");
        }));
        MemberEntity memberEntity = optionalMemberEntity.get();
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setCompanyName(memberDto.getCompanyName());
        memberEntity.setBusinessNumber(memberDto.getBusinessNumber());
        memberEntity.setPostcode(memberDto.getPostcode());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setDetailAddress(memberDto.getDetailAddress());
        memberEntity.setExtraAddress(memberDto.getExtraAddress());

        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .phone(memberEntity.getPhone())
                .postcode(memberEntity.getPostcode())
                .address(memberEntity.getAddress())
                .detailAddress(memberEntity.getDetailAddress())
                .extraAddress(memberEntity.getExtraAddress())
                .companyName(memberEntity.getCompanyName())
                .businessNumber(memberEntity.getBusinessNumber())
                .grade(memberEntity.getGrade())
                .build();
    }
}
