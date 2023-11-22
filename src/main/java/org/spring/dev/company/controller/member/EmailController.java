package org.spring.dev.company.controller.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.EmailMessage;
import org.spring.dev.company.dto.member.EmailPostDto;
import org.spring.dev.company.dto.member.EmailResponseDto;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.service.member.EmailService;
import org.spring.dev.company.service.member.AdminMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/send-mail")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final AdminMemberService memberService;


    // 임시 비밀번호 발급
    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestParam EmailPostDto emailPostDto) {
        System.out.println(emailPostDto + "아이고");
        System.out.println(emailPostDto.getEmail() + "아이고2");

        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[SAVIEW] 임시 비밀번호 발급")
                .build();

        emailService.sendMail(emailMessage, "password");

        return ResponseEntity.ok().build();
    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public ResponseEntity sendJoinMail(@RequestParam(required = false) EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[SAVIEW] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }

    @GetMapping("/check")
    public MemberDto check(@RequestParam(required = false) String email, @RequestParam(required = false) String name){

        boolean isMatching = memberService.checkEmailNameMatching(email,name);
        System.out.println("체크완");

        return new MemberDto(isMatching);
    }


}
