package org.spring.dev.company.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class MeowMungException extends RuntimeException{

    // 에러 메세지 저장
    public final Map<String, String> validation = new HashMap<>();

    // 메세지를 받아들이는 생성자
    public MeowMungException(String message) {
        super(message);
    }

    // 메세지와 원인을 받아들이는 또 다른 생성자
    public MeowMungException(String message, Throwable cause) {
        super(message, cause);
    }

    // 예외 상태 코드를 반환
    public abstract int getStatusCode();

    // 예외 객체에 유효성 검사 오류 추가
    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }


}
