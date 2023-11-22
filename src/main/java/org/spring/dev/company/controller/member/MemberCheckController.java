package org.spring.dev.company.controller.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.service.member.AdminMemberService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/join")
@RestController
@RequiredArgsConstructor
public class MemberCheckController {

    private final AdminMemberService memberService;

    @PostMapping("/emailCheck")
    public @ResponseBody int emailCheck(@RequestParam("email") String email){


        return memberService.emailCheck(email);

    }


    @PostMapping("/phoneCheck")
    public @ResponseBody int phoneCheck(@RequestParam("phone") String phone){
        return memberService.phoneNumCheck(phone);
    }

    @PostMapping("/companyCheck")
    public @ResponseBody int companyCheck(@RequestParam("companyName") String companyName){
        return memberService.companyNameCheck(companyName);
    }

    @PostMapping("/businessNumberCheck")
    public @ResponseBody int businessNumberCheck(@RequestParam("businessNumber") String businessNumber){
        return memberService.businessNumberCheck(businessNumber);
    }

}
