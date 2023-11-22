package org.spring.dev.company.service.approval.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.entity.approval.ApprovalStatus;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.ApproType;

@Getter
@NoArgsConstructor
public class ApprovalServiceCreate {

    private String title;

    private String content;

    private String type;

    @Builder
    private ApprovalServiceCreate(String title, String content, String type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public ApprovalEntity toEntity(MemberEntity member) {
        if (member.getGrade().equals(ApproType.COMPANY)) {
            return ApprovalEntity.builder()
                    .title(title)
                    .content(content)
                    .memberEntity(member)
                    .type(ApproType.FREELANCER)
                    .status(ApprovalStatus.UNAUTHORIZED)
                    .build();
        }

        return ApprovalEntity.builder()
                .title(title)
                .content(content)
                .memberEntity(member)
                .type(ApproType.COMPANY)
                .status(ApprovalStatus.UNAUTHORIZED)
                .build();
    }
}
