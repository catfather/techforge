package org.spring.dev.company.entity.accounts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.spring.dev.company.dto.accounts.AccountsDto;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "c_Accounts")
@Builder
public class AccountsEntity extends BaseEntity {

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

    public static AccountsEntity toEntity(AccountsDto accountsDto){
        return AccountsEntity.builder()
                .id(accountsDto.getId())
                .accountsStartDay(accountsDto.getAccountsStartDay())
                .accountsEndDay(accountsDto.getAccountsEndDay())
                .accountsPrice(accountsDto.getAccountsPrice())
                .memberId(accountsDto.getMemberId())
                .approvalId(accountsDto.getApprovalId())
                .build();

    }


}
