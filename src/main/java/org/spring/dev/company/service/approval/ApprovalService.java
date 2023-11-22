package org.spring.dev.company.service.approval;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.approval.response.ApprovalResponse;
import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.exception.ApprovalException;
import org.spring.dev.company.exception.MemberNotFound;
import org.spring.dev.company.repository.approval.ApprovalRepository;
import org.spring.dev.company.repository.member.MemberRepository;
import org.spring.dev.company.service.approval.request.ApprovalServiceCreate;
import org.spring.dev.company.service.approval.request.ApprovalServiceSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;

    private final MemberRepository memberRepository;

    // 결재 생성
    @Transactional
    public void create(Long id, ApprovalServiceCreate create) {

        MemberEntity member = memberRepository.findById(id).orElseThrow(MemberNotFound::new);

        ApprovalEntity approval = create.toEntity(member);

        approvalRepository.save(approval);
    }

    // 결재 승인, 미승인
    @Transactional
    public void approval(Long memberId, Long approvalId, String request, LocalDateTime start) {

        MemberEntity member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        ApprovalEntity approval = approvalRepository.findById(approvalId).orElseThrow(ApprovalException::new);

        if (approval.getType().equals(member.getGrade())) {
            approval.approval(start);

        } else throw new IllegalArgumentException("결재 권한이 없습니다.");
        System.out.println();
    }

    // 결재 리스트
    public List<ApprovalResponse> list(ApprovalServiceSearch search) {

        return approvalRepository.getList(search).stream()
                .map(ApprovalResponse::of)
                .collect(Collectors.toList());
    }

    // 결재 디테일
    public ApprovalResponse get(Long id) {

        ApprovalEntity approval = approvalRepository.findById(id).orElseThrow(ApprovalException::new);

        return ApprovalResponse.of(approval);
    }

    // 프로젝트 완료
    @Transactional
    public void projectComplete(Long id, LocalDateTime end) {
        ApprovalEntity approval = approvalRepository.findById(id).orElseThrow(ApprovalException::new);

        approval.complete(end);
    }

    public List<ApprovalResponse> projectList(ApprovalServiceSearch search, Long id) {

        return approvalRepository.findAllProject(search, id).stream()
                .map(ApprovalResponse::of)
                .collect(Collectors.toList());
    }

    public List<ApprovalResponse> payList(ApprovalServiceSearch search, Long id) {

        return approvalRepository.findAllPay(search, id).stream()
                .map(ApprovalResponse::of)
                .collect(Collectors.toList());
    }
}
