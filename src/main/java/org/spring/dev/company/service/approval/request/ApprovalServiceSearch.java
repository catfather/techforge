package org.spring.dev.company.service.approval.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
public class ApprovalServiceSearch {

    private static final int MAX_SIZE = 1000;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}