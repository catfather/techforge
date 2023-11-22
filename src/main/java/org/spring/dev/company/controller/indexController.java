package org.spring.dev.company.controller;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.dto.workTime.WorkTimeDto;
import org.spring.dev.company.entity.board.BoardEntity;
import org.spring.dev.company.entity.util.BaseEntity;
import org.spring.dev.company.entity.util.BoardType;
import org.spring.dev.company.repository.board.BoardRepository;
import org.spring.dev.company.service.workTime.WorkTimeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
@SessionAttributes("searchHistory")
public class indexController {

    private final BoardRepository boardRepository;

    // 로그인 페이지
    @GetMapping({"","/","//"})
    public String login(){
        return "member/loginList";
    }

//    main 페이지

    /*
    추후 멤버정보 가져오는거 수정필요
     */
    @ModelAttribute("searchHistory") // 검색 이력을 세션에 저장하기 위한 모델 어트리뷰트
    public List<String> initializeSearchHistory() {
        return new ArrayList<>();
    }

    @GetMapping("/home")
    public String index(Model model,
                        @AuthenticationPrincipal MyUserDetails myUserDetails,
                        @RequestParam(name = "boardType", required = false, defaultValue = "GENERAL") BoardType boardType,
                        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        @RequestParam(value = "search", required = false) String search,
                        @ModelAttribute("searchHistory") List<String> searchHistory){
//boardlist가져오기
        Page<BoardEntity> boardList;

        if(search != null && !search.isEmpty()){
            searchHistory.add(search);
            boardList = boardRepository.findByBoardTypeAndTitleContaining(boardType, search, pageable);
        }else{
            boardList = boardRepository.findByBoardType(boardType, pageable);
        }

        Long totalCount = boardList.getTotalElements();
        int pageSize = boardList.getSize();
        int nowPage = boardList.getNumber();
        int totalPage = boardList.getTotalPages();
        int blockNum = 3;
        int startPage = (int)((Math.floor(nowPage/blockNum)) + 1 < totalPage ? (Math.floor(nowPage/blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);


//        MemberDto member = memberService.detailMember(myUserDetails.getMemberEntity().getMemberId());
//        model.addAttribute("member", member);


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardList", boardList);
        model.addAttribute("boardType", boardType);
        model.addAttribute("key", "boardList");


        return "index";
    }
}
