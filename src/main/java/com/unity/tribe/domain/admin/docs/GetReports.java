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
@Operation(summary = "BO 신고 목록 조회")
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
                        "id":"신고 ID",
                        "userId":"신고 사용자 ID",
                        "targetType":"["USER", "FEED", "COMMNET"]",
                        "targetGroupId":"신고 대상 모임 ID",
                        "targetFeedId":"신고 대상 인증 ID",
                        "targetUserId":"신고 대상 사용자 ID",
                        "reportCount":"신고 횟수",
                        "reasonType":"[0, 1, 2, 3, 4]",
                        "status":"["PENDING", "RESOLVED", "REJECTED"]",
                        "createdAt":"2024-11-15T09:00:00"
                      },
                      {                        
                        "id":"신고 ID",
                        "userId":"신고 사용자 ID",
                        "targetType":"["USER", "FEED", "COMMNET"]",
                        "targetGroupId":"신고 대상 모임 ID",
                        "targetFeedId":"신고 대상 인증 ID",
                        "targetUserId":"신고 대상 사용자 ID",
                        "reportCount":"신고 횟수",
                        "reasonType":"[0, 1, 2, 3, 4]",
                        "status":"["PENDING", "RESOLVED", "REJECTED"]",
                        "createdAt":"2024-11-15T09:00:00"
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
public @interface GetReports {
}