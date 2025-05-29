package com.unity.tribe.domain.feed.docs;

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
@Operation(summary = "내 인증글 리스트 조회", description = """
        현재 로그인한 사용자의 인증글 리스트를 조회합니다.

        **정렬 옵션:**
        - createdAt,desc: 생성일 기준 최신순 (기본값)
        - createdAt,asc: 생성일 기준 오래된순
        - updatedAt,desc: 수정일 기준 최신순
        - updatedAt,asc: 수정일 기준 오래된순
        """)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "피드 목록 응답", value = """
                {
                  "success": true,
                  "message": "요청이 성공적으로 처리되었습니다.",
                  "data": {
                    "totalPages": 1,
                    "totalElements": 1,
                    "currentPage": 0,
                    "pageSize": 10,
                    "contents": [
                      {
                        "feedId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                        "groupId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                        "user": {
                            "userId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                            "nickname": "닉네임",
                            "profileImage": "https://example.com/image.png"
                        },
                        "feedType": "CERTIFICATION",
                        "content": "인증글 본문 내용입니다.",
                        "image": "https://example.com/image.png",
                        "status": "ACTIVE",
                        "createdAt": "2025-05-25T12:34:56",
                        "updatedAt": "2025-05-25T12:34:56"
                      }
                    ]
                  }
                }
                """)))
})
public @interface GetMyFeeds {
}