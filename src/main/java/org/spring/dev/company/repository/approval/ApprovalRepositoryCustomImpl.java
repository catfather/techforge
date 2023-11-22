package org.spring.dev.company.repository.approval;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.approval.response.ApprovalResponse;
import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.entity.approval.ApprovalStatus;
import org.spring.dev.company.entity.approval.ProjectStatus;
import org.spring.dev.company.entity.approval.QApprovalEntity;
import org.spring.dev.company.service.approval.request.ApprovalServiceSearch;

import java.util.List;

@RequiredArgsConstructor
public class ApprovalRepositoryCustomImpl implements ApprovalRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ApprovalEntity> getList(ApprovalServiceSearch search) {

        QApprovalEntity approval = QApprovalEntity.approvalEntity;

        return jpaQueryFactory.selectFrom(approval)
                .where(approval.status.eq(ApprovalStatus.UNAUTHORIZED))
                .limit(search.getSize())
                .offset(search.getOffset())
                .orderBy(approval.id.desc())
                .fetch();

    }

    @Override
    public List<ApprovalEntity> findAllProject(ApprovalServiceSearch search, Long id) {
        QApprovalEntity approval = QApprovalEntity.approvalEntity;

        return jpaQueryFactory.selectFrom(approval)
                .where(
                        approval.projectStatus.eq(ProjectStatus.INCOMPLETE)
                                .and(approval.memberEntity.id.eq(id))
                )
                .limit(search.getSize())
                .offset(search.getOffset())
                .orderBy(approval.id.desc())
                .fetch();
    }

    @Override
    public List<ApprovalEntity> findAllPay(ApprovalServiceSearch search, Long id) {

        QApprovalEntity approval = QApprovalEntity.approvalEntity;

        return jpaQueryFactory.selectFrom(approval)
                .where(
                        approval.projectStatus.eq(ProjectStatus.COMPLETE)
                                .and(approval.memberEntity.id.eq(id))
                )
                .limit(search.getSize())
                .offset(search.getOffset())
                .orderBy(approval.id.desc())
                .fetch();
    }
}
