package org.spring.dev.company.exception;

public class ApprovalException extends MeowMungException{
    private static final String MESSAGE = "결재 데이터를 가져오지 못했습니다.";

    public ApprovalException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
