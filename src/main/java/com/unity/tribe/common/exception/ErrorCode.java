package com.unity.tribe.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // === 공통 오류 ===
    INTERNAL_SERVER_ERROR("SYS_001", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR("SYS_002", "데이터베이스 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_API_ERROR("SYS_003", "외부 API 호출 중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    INVALID_INPUT_VALUE("SYS_004", "입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("SYS_005", "허용되지 않은 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    RESOURCE_NOT_FOUND("SYS_006", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // === 인증/인가 ===
    UNAUTHORIZED("AUTH_001", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("AUTH_002", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    INVALID_TOKEN("AUTH_003", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("AUTH_004", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    NAVER_LOGIN_FAILED("AUTH_005", "네이버 로그인 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    // === 사용자 도메인 ===
    USER_NOT_FOUND("USER_001", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_USER("USER_002", "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT),
    INVALID_USER_STATUS("USER_003", "사용자 상태가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // === 계정/회원가입 ===
    EMAIL_ALREADY_EXISTS("ACCOUNT_001", "이미 등록된 이메일입니다.", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS("ACCOUNT_002", "이미 등록된 휴대폰 번호입니다.", HttpStatus.CONFLICT),
    INVALID_PASSWORD("ACCOUNT_003", "비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // === 주문/결제 ===
    ORDER_NOT_FOUND("ORDER_001", "주문 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PAYMENT_FAILED("ORDER_002", "결제에 실패했습니다.", HttpStatus.BAD_REQUEST),

    // === 파일/업로드 ===
    FILE_UPLOAD_FAILED("FILE_001", "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_FOUND("FILE_002", "파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // === 그룹 ===
    GROUP_NOT_FOUND("GROUP_001", "존재하지 않는 그룹입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_GROUP("GROUP_002", "이미 존재하는 그룹입니다.", HttpStatus.CONFLICT),
    INVALID_GROUP_STATUS("GROUP_003", "그룹 상태가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // === 멤버 ===
    MEMBER_NOT_FOUND("MEMBER_001", "존재하지 않는 멤버입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_MEMBER("MEMBER_002", "이미 존재하는 멤버입니다.", HttpStatus.CONFLICT),
    INVALID_MEMBER_STATUS("MEMBER_003", "멤버 상태가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // === 기타 ===
    TOO_MANY_REQUESTS("SYS_007", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.", HttpStatus.TOO_MANY_REQUESTS);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
