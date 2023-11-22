package org.spring.dev.company.controller.member;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.member.MemberDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.service.member.AdminMemberService;
import org.spring.dev.company.service.member.EmailService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final AdminMemberService memberService;
    private final EmailService emailService;


    @GetMapping("/login")
    public String login() {
        // 관리자 로그인
        return "member/login";
    }

    @GetMapping("/companyLogin")
    public String companyLogin() {
        // 회사로그인
        return "company/companyLogin";
    }

    @GetMapping("/freelancerLogin")
    public String freelancerLogin() {
        // 프리렌서 로그인
        return "freelancer/freelancerLogin";
    }


    @GetMapping("/join")
    public String adminJoin() {
        // 관리자 회원가입페이지
        return "member/join";
    }

    @PostMapping("/adminJoin")
    public String memberJoin(@ModelAttribute MemberDto memberDto,
                             @RequestParam(name = "memberImg", required = false) List<MultipartFile> memberImg) {
//        System.out.println(memberDto.getEmail());

        Long rs = memberService.memberJoin(memberDto);
        if (rs == 0) {
            return "member/join";
        }
        return "member/login";
    }

    @GetMapping("/freeJoin")
    public String freeJoin() {
        // 프리렌서 회원가입
        return "freelancer/join";
    }

    @PostMapping("/freeJoin")
    public String freeJoinPost(@ModelAttribute MemberDto memberDto) {
//        System.out.println(memberDto.getEmail());

        Long rs = memberService.freeJoin(memberDto);
        if (rs == 0) {
            return "freelancer/join";
        }
        return "member/login";
    }

    @GetMapping("/companyJoin")
    public String companyJoin() {
        // 회사 회원가입
        return "company/join";
    }

    @PostMapping("/companyJoin")
    public String companyJoinPost(@ModelAttribute MemberDto memberDto) {
        Long rs = memberService.companyJoin(memberDto);
        if (rs == 0) {
            return "company/join";
        }
        return "member/login";
    }


    @GetMapping("/detail/{id}")
    public String detailMember(@PathVariable("id") Long memberId, Model model) {
        // 로그인 정보 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        MemberDto memberDto = memberService.detailMember(memberId);
        System.out.println(auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().matches(memberDto.getName())));
        System.out.println(auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().matches(memberDto.getEmail())));

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            if (isUserAuthorized(auth, memberDto) == 1) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffDetail";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffDetail";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    private int isUserAuthorized(Authentication auth, MemberDto memberDto) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (memberDto.getGrade().toString().equals("STAFF")) {
                return 1;
            }
            return 0;
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {
            if (memberDto.getGrade().toString().equals("STAFF") || memberDto.getGrade().toString().equals("ADMIN")) {
                return 0;
            } else {
                return 2;
            }

        }
        return 0;
    }


    @GetMapping("/up/{id}")
    public String updateMember(@PathVariable("id") Long memberId, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberDto memberDto = memberService.detailMember(memberId);
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            if (isUserAuthorized(auth, memberDto) == 1) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffUpdate";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffUpdate";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    @GetMapping("/findPw")
    public String findPwPage() {

        return "member/findPw";
    }

    @ResponseBody
    @PostMapping("/check")
    public int sendEmail(HttpServletRequest request, String email) {
        HttpSession session = request.getSession();
        emailService.mailSend(session, email);
        return 123;
    }

    @ResponseBody
    @PostMapping("/emailCheck")
    public boolean emailCertification(HttpServletRequest request, String email, String inputCode) {
        HttpSession session = request.getSession();

        boolean result = emailService.emailCertification(session, email, Integer.parseInt(inputCode));

//        return emailService.emailCertification(session, email, Integer.parseInt(inputCode));
        return result;
    }

    // 비번 찾기 - 새로운 비번 설정
    @PostMapping("/checkOk")
    public String checkOk(String email, String password) {
        emailService.updatePassword(email, password);

        return "member/login";
    }

    @PostMapping("/checkOk1")
    public void checkOk1(String email, String password) {
        emailService.updatePassword(email, password);

    }

    @GetMapping("/checkOk")
    public String checkOk(@RequestParam(value = "email") String email, Model model) {

        System.out.println(email + "이메일");
        model.addAttribute("email", email);


        return "member/checkOk";
    }


    @PostMapping("/update/{id}")
    public String upMember(@ModelAttribute MemberDto memberDto, Model model, @PathVariable("id") Long memberId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberDto memberDto1 = memberService.detailMember(memberId);
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            if (isUserAuthorized(auth, memberDto1) == 1) {
                MemberDto memberDto2 = memberService.updateMember(memberDto, memberId);
                model.addAttribute("memberDto", memberDto2);
                return "member/staffDetail";
            } else if (isUserAuthorized(auth, memberDto1) == 2) {
                MemberDto memberDto2 = memberService.updateMember(memberDto, memberId);
                model.addAttribute("memberDto", memberDto2);
                return "member/staffDetail";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    @GetMapping("/disabled/{memberId}")
    public String disabled(@PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto = memberService.detailMember(memberId);
            if (isUserAuthorized(auth, memberDto) == 1) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffDisabled";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffDisabled";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }


    @PostMapping("/disabled/{memberId}")
    public String disMember(@PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {
            MemberDto memberDto1 = memberService.detailMember(memberId);
            if (isUserAuthorized(auth, memberDto1) == 1) {
            MemberDto memberDto = memberService.disabledMember(memberId);
                model.addAttribute("memberDto", memberDto);
                return "member/staffDetail";
            } else if (isUserAuthorized(auth, memberDto1) == 2) {
            MemberDto memberDto = memberService.disabledMember(memberId);
                model.addAttribute("memberDto", memberDto);
                return "member/staffDetail";
            }
            return "member/authorityError";
        }
        return "member/authorityError";


    }


    // 비밀번호 변경
    @GetMapping("/pwChange/{memberId}")
    public String pwChange(@PathVariable("memberId") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto = memberService.detailMember(id);
            if (isUserAuthorized(auth, memberDto) == 1) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffPwChange";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "member/staffPwChange";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    @PostMapping("/pwChange/{memberId}")
    public String pwChangePost(@ModelAttribute MemberDto memberDto, @PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

                    MemberDto memberDto1 = memberService.detailMember(memberId);
            if (isUserAuthorized(auth, memberDto1) == 1) {
                int rs = memberService.passwordChange(memberDto, memberId);
                if (rs == 1) {
                    MemberDto memberDto2 = memberService.detailMember(memberId);
                    model.addAttribute("memberDto", memberDto2);
                    return "member/staffDetail";
                } else {
                    return "redirect:/member/pwChange/" + memberId;
                }
            } else if (isUserAuthorized(auth, memberDto1) == 2) {
                int rs = memberService.passwordChange(memberDto1, memberId);
                if (rs == 1) {
                    MemberDto memberDto2 = memberService.detailMember(memberId);
                    model.addAttribute("memberDto", memberDto2);
                    return "member/staffDetail";
                } else {
                    return "redirect:/member/pwChange/" + memberId;
                }
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    @PostMapping("/passCheck")
    public @ResponseBody boolean passwordCheck(@RequestParam("id") Long memberId, @RequestParam("password") String password) {

        boolean rs = memberService.passwordCheck(memberId, password);
        System.out.println(rs);
        return rs;
    }


    @GetMapping("/oauth2add")
    public String oauth2addOk(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        System.out.println("myUserDetails " + myUserDetails);

        Long id = myUserDetails.getMemberEntity().getId();

        System.out.println("아이고");
        System.out.println("myUserDetails.getMemberEntity" + myUserDetails.getMemberEntity());
        System.out.println("myUserDetails.getMemberEntity.getId" + myUserDetails.getMemberEntity().getId());

        MemberDto memberDto = memberService.memberUpdateOk(id);

        System.out.println("memberDto " + memberDto);
        model.addAttribute("memberDto", memberDto);

        return "member/oauth2add";

    }

    @PostMapping("/oauth2add")
    public String oauth2add(@AuthenticationPrincipal MyUserDetails myUserDetails, MemberDto memberDto) {
        int rs = memberService.memberUpdate(memberDto);

        return "redirect:/";
    }


    @GetMapping("/freeDetail/{memberId}")
    public String freeDetail(@PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto = memberService.detailMember(memberId);
            if (isUserAuthorized(auth, memberDto) == 1) {
                return "member/authorityError";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "freelancer/staffDetail";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }


    @GetMapping("/freeUp/{memberId}")
    public String freeUp(@PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto = memberService.detailMember(memberId);
            if (isUserAuthorized(auth, memberDto) == 1) {
                return "member/authorityError";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "freelancer/staffUpdate";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }


    @PostMapping("/freeUpdate/{memberId}")
    public String freeUpdate(@ModelAttribute MemberDto memberDto, @PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto1 = memberService.detailMember(memberId);
            if (isUserAuthorized(auth, memberDto1) == 1) {
                return "member/authorityError";
            } else if (isUserAuthorized(auth, memberDto1) == 2) {
            MemberDto memberDto2 = memberService.freeUpdate(memberDto, memberId);
                model.addAttribute("memberDto", memberDto2);
                return "freelancer/staffDetail";
            }
            return "member/authorityError";
        }
        return "member/authorityError";

    }


    @GetMapping("/companyDetail/{memberId}")
    public String companyDetail(@PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto = memberService.companyDetail(memberId);
            if (isUserAuthorized(auth, memberDto) == 1) {
                System.out.println("시발");
                return "member/authorityError";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                System.out.println("시발2");
                model.addAttribute("memberDto", memberDto);
                return "company/staffDetail";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    @GetMapping("/companyUp/{memberId}")
    public String companyUp(@PathVariable("memberId") Long memberId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {

            MemberDto memberDto = memberService.companyDetail(memberId);
            if (isUserAuthorized(auth, memberDto) == 1) {
                return "member/authorityError";
            } else if (isUserAuthorized(auth, memberDto) == 2) {
                model.addAttribute("memberDto", memberDto);
                return "company/staffUpdate";
            }
            return "member/authorityError";
        }
        return "member/authorityError";
    }

    @PostMapping("/companyUpdate/{memberId}")
    public String companyUpdatePost(@PathVariable("memberId") Long memberId, @ModelAttribute MemberDto memberDto, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MemberDto memberDto1 = memberService.companyDetail(memberId);
        if (isUserAuthorized(auth, memberDto1) == 1) {
            return "member/authorityError";
        } else if (isUserAuthorized(auth, memberDto1) == 2) {
            MemberDto memberDto2 = memberService.companyUpdate(memberDto, memberId);
            model.addAttribute("memberDto", memberDto2);
            return "company/staffDetail";
        }
        return "member/authorityError";
    }


    //    여기부터 LIST
    @GetMapping("/freeList")
    public String freeList(@PageableDefault(page = 0, size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(value = "subject", required = false) String subject,
                           @RequestParam(value = "search", required = false) String search,
                           Model model) {

        Page<MemberDto> memberList = memberService.pageFreeList(pageable, subject, search);


        Long totalCount = memberList.getTotalElements();
        int pagesize = memberList.getSize();
        int nowPage = memberList.getNumber();
        int totalPage = memberList.getTotalPages();
        int blockNum = 3;

        int startPage =
                (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage
                        ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);

        int endPage =
                (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);


        for (int i = startPage; i <= endPage; i++) {
            System.out.println(i + " , ");
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
//        model.addAttribute("postVo", postList);


        model.addAttribute("freeList", memberList);


        return "freelancer/freeLancerList";
    }

    @GetMapping("/companyList")
    public String companyList(@PageableDefault(page = 0, size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(value = "subject", required = false) String subject,
                              @RequestParam(value = "search", required = false) String search,
                              Model model) {

        Page<MemberDto> memberList = memberService.pageCompanyList(pageable, subject, search);


        Long totalCount = memberList.getTotalElements();
        int pagesize = memberList.getSize();
        int nowPage = memberList.getNumber();
        int totalPage = memberList.getTotalPages();
        int blockNum = 3;

        int startPage =
                (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage
                        ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);

        int endPage =
                (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);


        for (int i = startPage; i <= endPage; i++) {
            System.out.println(i + " , ");
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
//        model.addAttribute("postVo", postList);


        model.addAttribute("companyList", memberList);


        return "company/companyList";
    }

    @GetMapping("/staffList")
    public String staffList(@PageableDefault(page = 0, size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "subject", required = false) String subject,
                            @RequestParam(value = "search", required = false) String search,
                            Model model) {

        Page<MemberDto> memberList = memberService.pageStaffList(pageable, subject, search);


        Long totalCount = memberList.getTotalElements();
        int pagesize = memberList.getSize();
        int nowPage = memberList.getNumber();
        int totalPage = memberList.getTotalPages();
        int blockNum = 3;

        int startPage =
                (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage
                        ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);

        int endPage =
                (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);


        for (int i = startPage; i <= endPage; i++) {
            System.out.println(i + " , ");
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
//        model.addAttribute("postVo", postList);


        model.addAttribute("staffList", memberList);


        return "member/staffList";
    }


}





