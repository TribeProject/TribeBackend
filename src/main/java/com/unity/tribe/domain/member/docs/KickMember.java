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
@Operation(summary = "멤버 강제 탈퇴", description = "호스트가 특정 멤버를 그룹에서 강제 탈퇴시킵니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "강제 탈퇴 성공"),
        @ApiResponse(responseCode = "403", description = "호스트 권한 없음"),
        @ApiResponse(responseCode = "404", description = "멤버 또는 그룹 없음")
})
public @interface KickMember {
}