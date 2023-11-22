package org.spring.dev.company.service.accounts;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.accounts.AccountsDto;
import org.spring.dev.company.entity.accounts.AccountsEntity;
import org.spring.dev.company.repository.accounts.AccountsRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsDto postAccount(AccountsDto accountsDto) {

        AccountsEntity result = accountsRepository.save(AccountsEntity.toEntity(accountsDto));

        return AccountsDto.toDto(result);
    }
}
