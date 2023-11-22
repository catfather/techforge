package org.spring.dev.company.controller.approval;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.config.MyUserDetails;
import org.spring.dev.company.controller.approval.request.ApprovalCreate;
import org.spring.dev.company.controller.approval.request.ApprovalSearch;
import org.spring.dev.company.dto.approval.response.ApprovalResponse;
import org.spring.dev.company.service.approval.ApprovalService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    /**
     * 프로젝트 공고 올리기
     */
    @PostMapping("/api/v1/approval/create")
    public void create(@RequestBody @Valid ApprovalCreate request, @AuthenticationPrincipal MyUserDetails member) {
        approvalService.create(member.getMemberEntity().getId(), request.toServiceRequest());
    }

    /**
     * 회사나 프리랜서가 프로젝트를 참여한다.
     */
    @PatchMapping("/api/v1/approval/{approveId}")
    public void approval(@AuthenticationPrincipal MyUserDetails member, @PathVariable("approveId") Long approvalId,@RequestBody String request) {
        LocalDateTime start = LocalDateTime.now();
        approvalService.approval(member.getMemberEntity().getId(), approvalId, request, start);
    }

    /**
     * 현재 프로젝트 모집 글을 보여준다.
     */
    @GetMapping("/api/v1/approval")
    public List<ApprovalResponse> list(@ModelAttribute ApprovalSearch approvalSearch) {

        return approvalService.list(approvalSearch.toServiceRequest());
    }

    /**
     * 프로젝트 상세 정보를 본다.
     */
    @GetMapping("/api/v1/approval/{approvalId}")
    public ApprovalResponse get(@PathVariable("approvalId") Long id) {
        return approvalService.get(id);
    }

    /**
     * 현재 본인이 진행 중인 프로젝트를 본다.
     */
    @GetMapping("/api/v1/project")
    public List<ApprovalResponse> get(@AuthenticationPrincipal MyUserDetails member, ApprovalSearch search) {
        return approvalService.projectList(search.toServiceRequest(), member.getMemberEntity().getId());
    }

    @PostMapping("/api/v1/project/{approvalId}")
    public void projectEnd(@PathVariable("approvalId") Long approvalId) {
        LocalDateTime now = LocalDateTime.now();

        approvalService.projectComplete(approvalId, now);
    }

    @GetMapping("/api/v1/pay")
    public List<ApprovalResponse> payList(@AuthenticationPrincipal MyUserDetails member, ApprovalSearch search) {
        return approvalService.payList(search.toServiceRequest(), member.getMemberEntity().getId());
    }

}
