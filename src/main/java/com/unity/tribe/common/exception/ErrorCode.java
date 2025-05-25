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
    TOO_MANY_REQUESTS("SYS_007", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.", HttpStatus.TOO_MANY_REQUESTS),

    // === 인증/인가 ===
    UNAUTHORIZED("AUTH_001", "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("AUTH_002", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    INVALID_TOKEN("AUTH_003", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("AUTH_004", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),

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
    GROUP_FULL("GROUP_004", "그룹 정원이 가득 찼습니다.", HttpStatus.BAD_REQUEST),
    GROUP_ACCESS_DENIED("GROUP_005", "그룹에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // === 멤버 ===
    MEMBER_NOT_FOUND("MEMBER_001", "존재하지 않는 멤버입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_MEMBER("MEMBER_002", "이미 존재하는 멤버입니다.", HttpStatus.CONFLICT),
    INVALID_MEMBER_STATUS("MEMBER_003", "멤버 상태가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_HOST("MEMBER_004", "호스트 권한이 없습니다.", HttpStatus.FORBIDDEN),
    MEMBER_ALREADY_JOINED("MEMBER_005", "이미 가입된 멤버입니다.", HttpStatus.CONFLICT),
    MEMBER_CANNOT_KICK_SELF("MEMBER_006", "자기 자신을 강제 탈퇴시킬 수 없습니다.", HttpStatus.BAD_REQUEST),
    MEMBER_CANNOT_KICK_HOST("MEMBER_007", "호스트를 강제 탈퇴시킬 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_MEMBER_ROLE("MEMBER_008", "유효하지 않은 멤버 역할입니다.", HttpStatus.BAD_REQUEST),

    // === 차단 ===
    BLOCK_NOT_FOUND("BLOCK_001", "차단 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_BLOCKED("BLOCK_002", "이미 차단된 사용자입니다.", HttpStatus.CONFLICT),
    CANNOT_BLOCK_SELF("BLOCK_003", "자기 자신을 차단할 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_BLOCKED_USER("BLOCK_004", "차단되지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),
    BLOCKED_BY_USER("BLOCK_005", "해당 사용자에게 차단되었습니다.", HttpStatus.FORBIDDEN),

    // === 숨김 ===
    HIDE_NOT_FOUND("HIDE_001", "숨김 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_HIDDEN("HIDE_002", "이미 숨김 처리된 콘텐츠입니다.", HttpStatus.CONFLICT),
    NOT_HIDDEN_CONTENT("HIDE_003", "숨김 처리되지 않은 콘텐츠입니다.", HttpStatus.BAD_REQUEST),
    INVALID_HIDE_TARGET_TYPE("HIDE_004", "유효하지 않은 숨김 대상 타입입니다.", HttpStatus.BAD_REQUEST),
    CANNOT_HIDE_OWN_CONTENT("HIDE_005", "자신의 콘텐츠는 숨길 수 없습니다.", HttpStatus.BAD_REQUEST),

    // === 댓글 ===
    COMMENT_NOT_FOUND("COMMENT_001", "존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND),
    COMMENT_ALREADY_DELETED("COMMENT_002", "이미 삭제된 댓글입니다.", HttpStatus.BAD_REQUEST),
    COMMENT_ACCESS_DENIED("COMMENT_003", "댓글에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    COMMENT_TOO_LONG("COMMENT_004", "댓글이 너무 깁니다.", HttpStatus.BAD_REQUEST),
    COMMENT_EMPTY("COMMENT_005", "댓글 내용이 비어있습니다.", HttpStatus.BAD_REQUEST),
    PARENT_COMMENT_NOT_FOUND("COMMENT_006", "부모 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_COMMENT_DEPTH("COMMENT_007", "댓글 깊이가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    FEED_NOT_FOUND_FOR_COMMENT("COMMENT_008", "댓글을 작성할 피드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // === 신고 ===
    REPORT_NOT_FOUND("REPORT_001", "신고 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_REPORTED("REPORT_002", "이미 신고된 콘텐츠입니다.", HttpStatus.CONFLICT),
    CANNOT_REPORT_SELF("REPORT_003", "자신의 콘텐츠는 신고할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REPORT_TYPE("REPORT_004", "유효하지 않은 신고 타입입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REPORT_REASON("REPORT_005", "유효하지 않은 신고 사유입니다.", HttpStatus.BAD_REQUEST),
    REPORT_TARGET_NOT_FOUND("REPORT_006", "신고 대상을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // === 피드 ===
    FEED_NOT_FOUND("FEED_001", "존재하지 않는 피드입니다.", HttpStatus.NOT_FOUND),
    FEED_ACCESS_DENIED("FEED_002", "피드에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    FEED_ALREADY_DELETED("FEED_003", "이미 삭제된 피드입니다.", HttpStatus.BAD_REQUEST),
    INVALID_FEED_STATUS("FEED_004", "피드 상태가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // === 인증 ===
    CERTIFICATION_NOT_FOUND("CERT_001", "인증 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CERTIFICATION_EXPIRED("CERT_002", "인증이 만료되었습니다.", HttpStatus.BAD_REQUEST),
    CERTIFICATION_ALREADY_VERIFIED("CERT_003", "이미 인증된 상태입니다.", HttpStatus.CONFLICT),
    INVALID_CERTIFICATION_CODE("CERT_004", "유효하지 않은 인증 코드입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
