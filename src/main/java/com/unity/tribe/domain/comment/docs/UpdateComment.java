package com.unity.tribe.domain.comment.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.comment.dto.request.CommentUpdateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "댓글 수정", description = "기존 댓글의 내용을 수정합니다. 작성자만 수정할 수 있습니다.")
@RequestBody(description = "댓글 수정 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentUpdateRequestDto.class), examples = @ExampleObject(value = """
            {
              "content": "수정된 댓글 내용입니다."
            }
        """)))
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 200,
                      "message": "댓글이 성공적으로 수정되었습니다.",
                      "result": true,
                      "data": {
                        "commentId": 1,
                        "feedId": "01HXP6T1V9WJDKXEWQY6Y83XTB",
                        "user": {
                          "userId": "01HGW1MHT3RJYK5MXRZ6P5QACK",
                          "nickname": "닉네임",
                          "profileImage": "https://example.com/profile.jpg"
                        },
                        "content": "수정된 댓글 내용입니다.",
                        "parentCommentId": null,
                        "depth": 0,
                        "status": "ACTIVE",
                        "createdAt": "2025-05-25T10:00:00",
                        "updatedAt": "2025-05-25T10:30:00",
                        "deletedAt": null,
                        "replies": []
                      }
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "잘못된 요청입니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 401,
                      "message": "인증에 실패했습니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 403,
                      "message": "해당 댓글을 수정할 권한이 없습니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "해당 댓글을 찾을 수 없습니다.",
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
public @interface UpdateComment {
}