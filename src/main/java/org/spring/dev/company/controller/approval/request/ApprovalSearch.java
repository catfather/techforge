package org.spring.dev.company.controller.approval.request;

import lombok.Builder;
import lombok.Getter;
import org.spring.dev.company.service.approval.request.ApprovalServiceSearch;
import org.springframework.stereotype.Service;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
public class ApprovalSearch {

    private static final int MAX_SIZE = 1000;

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }


    public ApprovalServiceSearch toServiceRequest() {
        return ApprovalServiceSearch.builder()
                .page(page != null ? page : 0)
                .size(size != null ? size : 10)
                .build();
    }
}