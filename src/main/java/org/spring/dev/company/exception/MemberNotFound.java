package org.spring.dev.company.exception;

public class MemberNotFound extends MeowMungException {

    private static final String MESSAGE = "회원을 찾을 수 없습니다.";

    public MemberNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
