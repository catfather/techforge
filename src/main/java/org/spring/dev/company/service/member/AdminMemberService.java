package org.spring.dev.company.service.member;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.member.MemberRepository;
import org.spring.dev.company.repository.member.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public int emailCheck(String email) {

        int memberRs = memberRepository.findByEmail1(email);
        return memberRs;
    }

    public int phoneNumCheck(String phone) {
        int memberRs = memberRepository.findByPhone(phone);


        return memberRs;
    }

    @Transactional
    public Long memberJoin(MemberDto memberDto) {


        MemberEntity memberEntity = MemberEntity.toMember(memberDto, passwordEncoder);
        Long rs = memberRepository.save(memberEntity).getId();

        memberRepository.findById(rs);

        return rs;
    }

    public MemberDto detailMember(Long memberId) {
        Optional<MemberEntity> memberEntity =
                Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new));

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

    public boolean userEmailCheck(String email, String name) {

        MemberEntity memberEntity = memberRepository.findUserByEmail(email);

        if (memberEntity != null && memberEntity.getName().equals(name)) {
            return true;
        } else {
            return false;
        }

    }

    public void SetTempPassword(String memberEmail, String newPassword) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isPresent()) {

            MemberEntity memberEntity = optionalMemberEntity.get();
            String password = passwordEncoder.encode(newPassword);
            memberEntity.setPassword(password);
            memberRepository.save(memberEntity);

        } else {
//            log
        }
    }

    public boolean checkEmailNameMatching(String email, String name) {
        MemberEntity memberEntity = memberRepository.findByEmailAndName(email, name);
        return memberEntity != null;


    }

    @Transactional
    public MemberDto updateMember(MemberDto memberDto, Long memberId) {
        Optional<MemberEntity> memberEntity =
                Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new));


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

    @Transactional
    public MemberDto disabledMember(Long memberId) {
        Optional<MemberEntity> memberEntity =
                Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new));
        if (memberEntity.get().getIs_display() != 0) {

            MemberEntity memberEntity1 = memberEntity.get();
            memberEntity1.setIs_display(0);
            memberRepository.save(memberEntity1);
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
                .id(memberEntity.get().getId())
                .name(memberEntity.get().getName())
                .email(memberEntity.get().getEmail())
                .password(memberEntity.get().getPassword())
                .birth(memberEntity.get().getBirth())
                .phone(memberEntity.get().getPhone())
                .postcode(memberEntity.get().getPostcode())
                .address(memberEntity.get().getAddress())
                .detailAddress(memberEntity.get().getDetailAddress())
                .extraAddress(memberEntity.get().getExtraAddress())
                .gender(memberEntity.get().getGender())
                .grade(memberEntity.get().getGrade())
                .CreateTime(memberEntity.get().getCreateTime())
                .is_display(memberEntity.get().getIs_display())
                .CreateTime(memberEntity.get().getCreateTime())
                .UpdateTime(memberEntity.get().getUpdateTime())
                .build();
    }

    public Page<MemberDto> pageFreeList(Pageable pageable, String subject, String search) {

        Page<MemberEntity> memberEntities = null;

        if (subject == null) {
//            memberEntities = memberRepository.findAll(pageable);
            memberEntities = memberRepository.findFreeAll(pageable);
        } else if (subject.equals("name")) {
//            memberEntities = memberRepository.findByNameContains(pageable, search);
            memberEntities = memberRepository.findByNameContains(pageable, search);
        } else if (subject.equals("email")) {
            memberEntities = memberRepository.findByEmailContains(pageable, search);
        } else if (subject.equals("phone")) {
            memberEntities = memberRepository.findByPhoneContains(pageable, search);
        } else {
            memberEntities = memberRepository.findFreeAll(pageable);
        }

        Page<MemberDto> memberDtos = memberEntities.map(MemberDto::toMemberDto);

        return memberDtos;
    }

    public Page<MemberDto> pageCompanyList(Pageable pageable, String subject, String search) {

        Page<MemberEntity> memberEntities = null;

        if (subject == null) {
            memberEntities = memberRepository.findCompanyAll(pageable);
        } else if (subject.equals("name")) {
            memberEntities = memberRepository.findByNameCompany(pageable, search);
        } else if (subject.equals("email")) {
            memberEntities = memberRepository.findByEmailCompany(pageable, search);
        } else if (subject.equals("phone")) {
            memberEntities = memberRepository.findByPhoneCompany(pageable, search);
        } else {
            memberEntities = memberRepository.findFreeAll(pageable);
        }

        Page<MemberDto> memberDtos = memberEntities.map(MemberDto::toMemberDto);

        return memberDtos;
    }

    public Page<MemberDto> pageStaffList(Pageable pageable, String subject, String search) {

        Page<MemberEntity> memberEntities = null;

        if (subject == null) {
            memberEntities = memberRepository.findStaffAll(pageable);
        } else if (subject.equals("name")) {
            memberEntities = memberRepository.findByNameStaff(pageable, search);
        } else if (subject.equals("email")) {
            memberEntities = memberRepository.findByEmailStaff(pageable, search);
        } else if (subject.equals("phone")) {
            memberEntities = memberRepository.findByPhoneStaff(pageable, search);
        } else {
            memberEntities = memberRepository.findStaffAll(pageable);
        }

        Page<MemberDto> memberDtos = memberEntities.map(MemberDto::toMemberDto);

        return memberDtos;
    }



    @Transactional
    public MemberDto memberUpdateOk(Long id) {
        // MemberEntity id 확인
        Optional<MemberEntity> optionalMemberEntity =
                Optional.ofNullable(memberRepository.findById(id).orElseThrow(() -> {
                    return new IllegalArgumentException("수정할 아이디가 없습니다.");
                }));

        MemberEntity memberEntity = optionalMemberEntity.get();

        if (optionalMemberEntity.isPresent()) {

            MemberDto memberDto = MemberDto.builder()
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .birth(memberEntity.getBirth())
                    .email(memberEntity.getEmail())
                    .career(memberEntity.getCareer())
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
    public int memberUpdate(MemberDto memberDto) {
        Optional<MemberEntity> optionalMemberEntity =
                Optional.ofNullable(memberRepository.findById(memberDto.getId()).orElseThrow(() -> {
                    return new IllegalArgumentException("수정할 아이디가 없습니다.");
                }));

        MemberEntity memberEntity = MemberEntity.toupdate(memberDto, passwordEncoder);


        Long id = memberRepository.save(memberEntity).getId();

        Optional<MemberEntity> optionalMemberEntity2 =
                Optional.ofNullable(memberRepository.findById(id).orElseThrow(() -> {
                    return new IllegalArgumentException("수정 아이디가 없습니다.");
                }));

        if (optionalMemberEntity2.isPresent()) {
            // 수정이 정상 실행
            return 1;
        }
        return 0;
    }

    @Transactional
    public int passwordChange(MemberDto memberDto, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
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


    public boolean passwordCheck(Long memberId, String password) {
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new));

        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return passwordEncoder.matches(password, memberEntity.getPassword());
        } else {
            return false;
        }

    }
    @Transactional
    public Long freeJoin(MemberDto memberDto) {
        MemberEntity memberEntity = MemberEntity.toFree(memberDto, passwordEncoder);
        Long rs = memberRepository.save(memberEntity).getId();
        return rs;
    }

    @Transactional
    public Long companyJoin(MemberDto memberDto) {
        MemberEntity memberEntity = MemberEntity.toCompany(memberDto, passwordEncoder);
        return memberRepository.save(memberEntity).getId();
    }


    public int companyNameCheck(String companyName) {
        int rs = memberRepository.findByCompanyName(companyName);
        return rs;
    }

    public int businessNumberCheck(String businessNumber) {
        int rs = memberRepository.findByBusinessNumber(businessNumber);
        return rs;
    }

    @Transactional
    public MemberDto freeUpdate(MemberDto memberDto, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
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

    public MemberDto companyDetail(Long memberId){
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
                    return new IllegalArgumentException("아이디가 없습니다.");
        }));

        return MemberDto.builder()
                .id(optionalMemberEntity.get().getId())
                .name(optionalMemberEntity.get().getName())
                .email(optionalMemberEntity.get().getEmail())
                .phone(optionalMemberEntity.get().getPhone())
                .postcode(optionalMemberEntity.get().getPostcode())
                .address(optionalMemberEntity.get().getAddress())
                .detailAddress(optionalMemberEntity.get().getDetailAddress())
                .extraAddress(optionalMemberEntity.get().getExtraAddress())
                .companyName(optionalMemberEntity.get().getCompanyName())
                .businessNumber(optionalMemberEntity.get().getBusinessNumber())
                .grade(optionalMemberEntity.get().getGrade())
                .build();
    }
    @Transactional
    public MemberDto companyUpdate(MemberDto memberDto, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity
                = Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(()->{
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
                .build();
    }

}
