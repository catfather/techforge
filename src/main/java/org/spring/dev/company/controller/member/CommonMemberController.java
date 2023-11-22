package org.spring.dev.company.controller.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.service.member.CommonMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/common")
@RequiredArgsConstructor
@Controller
public class CommonMemberController {

    private final CommonMemberService commonMemberService;


    @GetMapping("/detail")
    public String detailMember(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "member/detail";

    }

    @GetMapping("/up")
    public String updateMember(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "member/update";
    }

    @PostMapping("/update")
    public String upMember(@ModelAttribute MemberDto memberDto, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        MemberDto memberDto1 = commonMemberService.updateMember(memberDto, myUserDetails);
        model.addAttribute("memberDto", memberDto1);
        return "member/detail";
    }

    @GetMapping("/disabled")
    public String disabled(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "member/disabled";
    }

    @PostMapping("/disabled")
    public String disMember(@AuthenticationPrincipal MyUserDetails myUserDetails) {

        int rs = commonMemberService.disabledMember(myUserDetails);
        if (rs != 0) {
            return "member/detail";
        }
        return "member/m";

    }

    @GetMapping("/pwChange")
    public String pwChange(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "member/pwChange";
    }

    @PostMapping("/pwChange")
    public String pwChangePost(@ModelAttribute MemberDto memberDto, MyUserDetails myUserDetails, Model model) {
        int rs = commonMemberService.passwordChange(memberDto, myUserDetails);
        if (rs != 1) {
            MemberDto memberDto1 = commonMemberService.detailMember(myUserDetails);
            model.addAttribute("memberDto", memberDto1);
            return "member/detail";
        }
        return "redirect:/member/logout";
    }

    @GetMapping("/freeDetail")
    public String freeDetail(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "freeDetail/detail";
    }

    @GetMapping("/freeUp")
    public String freeUp(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "freelancer/update";
    }

    @PostMapping("/freeUpdate")
    public String freeUpdate(@ModelAttribute MemberDto memberDto, @AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        MemberDto memberDto1 = commonMemberService.freeUpdate(memberDto, myUserDetails);
        model.addAttribute("memberDto", memberDto1);
        return "freelancer/detail";
    }

    @GetMapping("/companyDetail")
    public String companyDetail(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "company/detail";
    }

    @GetMapping("/companyUp")
    public String companyUp(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        MemberDto memberDto = commonMemberService.detailMember(myUserDetails);
        model.addAttribute("memberDto", memberDto);
        return "company/update";
    }

    @PostMapping("companyUpdate")
    public String companyUpdatePost(@AuthenticationPrincipal MyUserDetails myUserDetails, @ModelAttribute MemberDto memberDto, Model model) {
        MemberDto memberDto1 = commonMemberService.companyUpdate(memberDto, myUserDetails);
        model.addAttribute("memberDto", memberDto1);
        return "company/detail";
    }


}
