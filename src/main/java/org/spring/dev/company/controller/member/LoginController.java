package org.spring.dev.company.controller.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.repository.member.LoginRepository;
import org.spring.dev.company.repository.member.MemberRepository;
import org.spring.dev.company.service.member.LoginService;
import org.spring.dev.company.service.member.AdminMemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AdminMemberService memberService;
    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final LoginRepository loginRepository;


    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {

      /*  String errorMessage = null;

        if (error != null) {

            errorMessage = (String) request.getSession().getAttribute("errorMessage");
            System.out.println(error+" error");
            System.out.println(errorMessage+" errorMessage");

            request.getSession().removeAttribute("errorMessage");  // 에러 메시지 세션에서 제거
        }
*/
        System.out.println(error+" error1");
        System.out.println(exception+" exception");
        model.addAttribute("error", error );
        model.addAttribute("exception", exception);

        return "member/login";

    }

//    @GetMapping("/oauth2add")
//    public String oauth2addOk(@AuthenticationPrincipal MyUserDetails myUserDetails,Model model){
//
//        System.out.println("myUserDetails " + myUserDetails);
//
//        Long memberId = myUserDetails.getMemberEntity().getId();
//
//        System.out.println("myUserDetails.getMemberEntity" + myUserDetails.getMemberEntity());
//        System.out.println("myUserDetails.getMemberEntity.getId" + myUserDetails.getMemberEntity().getId());
//
//        MemberDto memberDto = loginService.loginUpdateOk(memberId);
//
//        System.out.println("memberDto " + memberDto);
//        model.addAttribute("member", memberDto);
//
//        return "/member/oauth2add";
//
//    }

    @PostMapping("/oauth2add")
    public String oauth2add(@AuthenticationPrincipal MyUserDetails myUserDetails, MemberDto memberDto){
        int rs = loginService.loginUpdate(memberDto);


        return "redirect:/";
    }






}





