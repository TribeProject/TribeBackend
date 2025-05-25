package com.unity.tribe.domain.comment.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.comment.dto.request.CommentCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "댓글 생성", description = "새로운 댓글을 등록합니다. 대댓글의 경우 parentCommentId를 포함하여 요청합니다.")
@RequestBody(description = "댓글 생성 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentCreateRequestDto.class), examples = {
        @ExampleObject(name = "일반 댓글", value = """
                    {
                      "content": "댓글 내용입니다."
                    }
                """),
        @ExampleObject(name = "대댓글", value = """
                    {
                      "content": "대댓글 내용입니다.",
                      "parentCommentId": 1
                    }
                """)
}))
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "댓글 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 201,
                      "message": "댓글이 성공적으로 생성되었습니다.",
                      "result": true,
                      "data": {
                        "commentId": 1,
                        "feedId": "01HXP6T1V9WJDKXEWQY6Y83XTB",
                        "user": {
                          "userId": "01HGW1MHT3RJYK5MXRZ6P5QACK",
                          "nickname": "닉네임",
                          "profileImage": "https://example.com/profile.jpg"
                        },
                        "content": "인증 확인했어요.",
                        "parentCommentId": null,
                        "depth": 0,
                        "status": "ACTIVE",
                        "createdAt": "2025-05-25T10:00:00",
                        "updatedAt": "2025-05-25T10:00:00",
                        "deletedAt": null,
                        "replies": []
                      }
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "잘못된 요청입니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 401,
                      "message": "인증에 실패했습니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "해당 피드를 찾을 수 없습니다.",
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
public @interface CreateComment {
}