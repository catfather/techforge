package org.spring.dev.company.entity.approval;

import lombok.AccessLevel;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.entity.util.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "c_approval")
public class ApprovalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long id;

    @Column(name = "approval_title")
    private String title;

    @Column(name = "approval_content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_type")
    private ApproType type;

    // n:1 한명의 회원이 여러개의 결재문서
    @ManyToOne
    private MemberEntity memberEntity;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    private LocalDateTime start;

    private LocalDateTime end;


    @Builder
    private ApprovalEntity(Long id, String title, String content, ApproType type, MemberEntity memberEntity, ApprovalStatus status, ProjectStatus projectStatus, LocalDateTime start, LocalDateTime end) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.memberEntity = memberEntity;
        this.status = status;
        this.projectStatus = projectStatus != null ? ProjectStatus.COMPLETE : ProjectStatus.INCOMPLETE;
        this.start = start;
        this.end = end;
    }

    public void approval(LocalDateTime start) {
            this.start = start;
            this.status = ApprovalStatus.APPROVAL;
    }

    public void complete(LocalDateTime end) {
        this.end = end;
        this.projectStatus = ProjectStatus.COMPLETE;
    }

}
