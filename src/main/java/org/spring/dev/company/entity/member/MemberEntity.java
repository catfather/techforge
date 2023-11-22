package org.spring.dev.company.entity.member;

import lombok.*;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.entity.util.BaseEntity;
import org.spring.dev.company.entity.util.GenderEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "c_member")
public class MemberEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id" )
    private Long id;

    @Column(name = "member_name",nullable = false)
    private String name;

    @Column(name = "member_birth")
    private String birth;

    @Column(name = "member_email",nullable = false)
    private String email;

    @Column(name = "member_nick",nullable = true)
    private String nickName;

    @Column(name = "member_phone",nullable = false)
    private String phone;

    @Column(name = "member_password",nullable = false)
    private String password;

    @Column(name = "member_postcode",nullable = false)
    private String postcode;

    @Column(name = "member_address",nullable = false)
    private String address;

    @Column(name = "member_detailAddress",nullable = false)
    private String detailAddress;

    @Column(name = "member_extraAddress",nullable = false)
    private String extraAddress;

    // 프리렌서 관련
    @Column(name = "member_career", nullable = true)
    private String career;

    //회사 관련
    @Column(name = "company_name", nullable = true)
    private String companyName;
    // 사업자번호
    @Column(name = "business_number", nullable = true)
    private String businessNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_grade")
    private ApproType grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_gender")
    private GenderEntity gender;

    @Column(name = "member_matching")
    private boolean matching;


    public static MemberEntity toMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(memberDto.getName());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberEntity.setBirth(memberDto.getBirth());
        memberEntity.setPostcode(memberDto.getPostcode());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setDetailAddress(memberDto.getDetailAddress());
        memberEntity.setExtraAddress(memberDto.getExtraAddress());
        memberEntity.setGrade(ApproType.STAFF);
        memberEntity.setGender(memberDto.getGender());
        memberEntity.setIs_display(memberDto.getIs_display());
        return memberEntity;
    }

    // Oauth 로그인시
    public static MemberEntity toOauth(String email, String name, String password) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(name);
        memberEntity.setEmail(email);
        memberEntity.setPhone("*");
        memberEntity.setPassword(password);
        memberEntity.setBirth("*");
        memberEntity.setPostcode("*");
        memberEntity.setAddress("*");
        memberEntity.setDetailAddress("*");
        memberEntity.setExtraAddress("*");
        memberEntity.setGrade(ApproType.FREELANCER);
        memberEntity.setIs_display(1);
        return memberEntity;
    }

    // OAuth2 추가 정보 입력 후 업데이트
    public static MemberEntity toupdate(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setName(memberDto.getName());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setPassword(memberDto.getPassword());
        memberEntity.setCareer(memberDto.getCareer());
        memberEntity.setBirth(memberDto.getBirth());
        memberEntity.setPostcode(memberDto.getPostcode());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setDetailAddress(memberDto.getDetailAddress());
        memberEntity.setExtraAddress(memberDto.getExtraAddress());
        memberEntity.setGrade(ApproType.FREELANCER);
        memberEntity.setGender(memberDto.getGender());
        memberEntity.setMatching(memberDto.isMatching());
        return memberEntity;
    }

    public static MemberEntity toFree(MemberDto memberDto, PasswordEncoder passwordEncoder) {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(memberDto.getName());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberEntity.setCareer(memberDto.getCareer());
        memberEntity.setBirth(memberDto.getBirth());
        memberEntity.setPostcode(memberDto.getPostcode());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setDetailAddress(memberDto.getDetailAddress());
        memberEntity.setExtraAddress(memberDto.getExtraAddress());
        memberEntity.setGrade(ApproType.FREELANCER);
        memberEntity.setGender(memberDto.getGender());
        memberEntity.setIs_display(1);
        return memberEntity;
    }



    public static MemberEntity toCompany(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(memberDto.getName());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPhone(memberDto.getPhone());
        memberEntity.setCompanyName(memberDto.getCompanyName());
        memberEntity.setBusinessNumber(memberDto.getBusinessNumber());
        memberEntity.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberEntity.setPostcode(memberDto.getPostcode());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setDetailAddress(memberDto.getDetailAddress());
        memberEntity.setExtraAddress(memberDto.getExtraAddress());
        memberEntity.setGrade(ApproType.COMPANY);
        memberEntity.setIs_display(1);
        return memberEntity;
    }
}
