package org.spring.dev.company.dto.accounts;

import lombok.*;
import org.spring.dev.company.entity.accounts.AccountsEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime accountsStartDay;

    @NotNull
    private LocalDateTime accountsEndDay;

    @NotNull
    private Integer accountsPrice;

    @NotNull
    private Long memberId;

    @NotNull
    private Long approvalId;


    public static AccountsDto toDto(AccountsEntity accountsEntity){
        return AccountsDto.builder()
                .id(accountsEntity.getId())
                .accountsStartDay(accountsEntity.getAccountsStartDay())
                .accountsEndDay(accountsEntity.getAccountsEndDay())
                .accountsPrice(accountsEntity.getAccountsPrice())
                .memberId(accountsEntity.getMemberId())
                .approvalId(accountsEntity.getApprovalId())
                .build();
    }

}
