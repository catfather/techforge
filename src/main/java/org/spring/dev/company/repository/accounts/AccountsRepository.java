package org.spring.dev.company.repository.accounts;

import org.spring.dev.company.entity.accounts.AccountsEntity;
import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.repository.approval.ApprovalRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<AccountsEntity, Long>{
}
