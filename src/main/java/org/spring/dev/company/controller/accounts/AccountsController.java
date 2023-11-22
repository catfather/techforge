package org.spring.dev.company.controller.accounts;


import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.accounts.AccountsDto;
import org.spring.dev.company.service.accounts.AccountsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    /*
    TODO
    memberId 별로 조회
     */
    @GetMapping("/{memberId}")
    public String index(
            @PathVariable(name = "memberId") Long memberId){

    return "accounts/index";
    }

    // 정산 생성 = 정산 하기

    @PostMapping("/insert")
    @ResponseBody
    public AccountsDto  postAccounts(
            @RequestBody AccountsDto accountsDto){

        return accountsService.postAccount(accountsDto);
    }



}
