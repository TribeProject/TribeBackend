package com.unity.tribe.domain.group.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Tag(name = "모임 API", description = "모임 관련 API")
@Operation(summary = "모임 API", description = "모임 생성, 조회, 수정, 삭제 및 참여/탈퇴 API")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "요청 성공"),
    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
    @ApiResponse(responseCode = "401", description = "인증되지 않은 요청"),
    @ApiResponse(responseCode = "403", description = "권한이 없는 요청"),
    @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음"),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
})
public @interface GroupSwaggerAnnotation {
    @Parameter(description = "그룹 ID", required = true)
    String groupId() default "";

    @Parameter(description = "페이지 번호", required = false)
    int page() default 0;

    @Parameter(description = "페이지 크기", required = false)
    int size() default 10;
} 