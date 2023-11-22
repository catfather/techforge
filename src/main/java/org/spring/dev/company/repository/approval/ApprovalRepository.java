package org.spring.dev.company.repository.approval;

import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.entity.util.ApproType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long>, ApprovalRepositoryCustom {

    List<ApprovalEntity> findAllByType(ApproType approType);
}
