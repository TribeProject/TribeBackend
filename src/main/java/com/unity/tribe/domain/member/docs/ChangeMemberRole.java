package com.unity.tribe.domain.member.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "멤버 역할 변경", description = "호스트가 그룹 내 멤버의 역할을 변경합니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "변경 성공"),
        @ApiResponse(responseCode = "403", description = "호스트 권한 없음"),
        @ApiResponse(responseCode = "400", description = "잘못된 역할 요청")
})
public @interface ChangeMemberRole {
}