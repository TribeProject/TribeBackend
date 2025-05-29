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
@Operation(summary = "피드 검색", description = "키워드로 인증(피드)를 검색합니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "피드 목록 응답", value = """
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
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "user": {
                            "userId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
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
public @interface SearchFeeds {
}