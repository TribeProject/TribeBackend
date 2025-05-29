package com.unity.tribe.domain.admin.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "BO 사용자 목록 조회")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 200,
                  "message": "요청이 성공했습니다.",
                  "result": true,
                  "data": {
                    "page": 0,
                    "size": 2,
                    "totalElements": 2,
                    "totalPages": 1,
                    "data": [
                      {
                        "userId" : "유저 ID",
                        "userName" : "유저명",
                        "gender" : "[MALE, FEMALE]",
                        "email" : "example@domain",
                        "joinType" : "[KAKAO, NAVER]",
                        "createdAt" : "2022-01-01 00:00:00",
                        "updatedAt" : "2022-01-01 00:00:00",
                        "deletedAt" : "2022-01-01 00:00:00",
                        "status" : "[ACTIVE, INACTIVE, DELETED]"
                      },
                      {                        
                        "userId" : "유저 ID",
                        "userName" : "유저명",
                        "gender" : "[MALE, FEMALE]",
                        "email" : "example@domain",
                        "joinType" : "[KAKAO, NAVER]",
                        "createdAt" : "2022-01-01 00:00:00",
                        "updatedAt" : "2022-01-01 00:00:00",
                        "deletedAt" : "2022-01-01 00:00:00",
                        "status" : "[ACTIVE, INACTIVE, DELETED]"
                      }                    
                    ]
                  }
                }
                """))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 401,
                  "message": "인증이 필요합니다.",
                  "result": false,
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 500,
                  "message": "서버 오류가 발생했습니다.",
                  "result": false,
                  "data": null
                }
                """)))
})
public @interface GetUsers {
}