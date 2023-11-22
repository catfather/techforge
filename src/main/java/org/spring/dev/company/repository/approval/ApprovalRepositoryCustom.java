package org.spring.dev.company.repository.approval;

import org.spring.dev.company.dto.approval.response.ApprovalResponse;
import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.service.approval.request.ApprovalServiceSearch;

import java.util.Arrays;
import java.util.List;

public interface ApprovalRepositoryCustom {
    List<ApprovalEntity> getList(ApprovalServiceSearch search);

    List<ApprovalEntity> findAllProject(ApprovalServiceSearch search, Long id);

    List<ApprovalEntity> findAllPay(ApprovalServiceSearch search, Long id);
}
