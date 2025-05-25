package com.unity.tribe.domain.comment.docs;

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
@Operation(summary = "댓글 목록 조회", description = """
        특정 피드의 댓글 목록을 조회합니다.

        **특징:**
        - 최상위 댓글을 페이징으로 조회
        - 각 댓글의 대댓글도 함께 반환
        - 생성일 기준 오름차순 정렬
        """)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "댓글 목록 응답", value = """
                    {
                      "success": true,
                      "message": "요청이 성공적으로 처리되었습니다.",
                      "data": {
                        "totalPages": 1,
                        "totalElements": 2,
                        "currentPage": 0,
                        "pageSize": 10,
                        "contents": [
                          {
                            "commentId": 1,
                            "feedId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                            "user": {
                              "userId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                              "nickname": "사용자1",
                              "profileImage": "https://example.com/profile1.jpg"
                            },
                            "content": "인증 확인했어요.",
                            "parentCommentId": null,
                            "depth": 0,
                            "status": "ACTIVE",
                            "createdAt": "2025-05-25T10:00:00",
                            "updatedAt": "2025-05-25T10:00:00",
                            "deletedAt": null,
                            "replies": [
                              {
                                "commentId": 3,
                                "feedId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                                "user": {
                                  "userId": "01HZTK7GZHCNRVZ1VP2YHZ9ZN1",
                                  "nickname": "사용자2",
                                  "profileImage": "https://example.com/profile2.jpg"
                                },
                                "content": "감사합니다.",
                                "parentCommentId": 1,
                                "depth": 1,
                                "status": "ACTIVE",
                                "createdAt": "2025-05-25T10:30:00",
                                "updatedAt": "2025-05-25T10:30:00",
                                "deletedAt": null,
                                "replies": null
                              }
                            ]
                          },
                          {
                            "commentId": 2,
                            "feedId": "01HZTK7GZHCNRVZ1VP2YHZ9ZNY",
                            "user": {
                              "userId": "01HZTK7GZHCNRVZ1VP2YHZ9ZN2",
                              "nickname": "사용자3",
                              "profileImage": "https://example.com/profile3.jpg"
                            },
                            "content": "좋은 인증글이네요!",
                            "parentCommentId": null,
                            "depth": 0,
                            "status": "ACTIVE",
                            "createdAt": "2025-05-25T10:15:00",
                            "updatedAt": "2025-05-25T10:15:00",
                            "deletedAt": null,
                            "replies": []
                          }
                        ]
                      }
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "해당 피드를 찾을 수 없습니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 500,
                      "message": "서버 오류가 발생했습니다.",
                      "result": false,
                      "data": null
                    }
                """)))
})
public @interface GetComments {
}