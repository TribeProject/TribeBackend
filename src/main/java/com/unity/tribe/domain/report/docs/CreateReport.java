package com.unity.tribe.domain.report.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.report.dto.ReportCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "신고 생성", description = "피드, 댓글, 사용자에 대한 신고를 생성합니다.", requestBody = @RequestBody(required = true, description = "신고 생성 요청 정보", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReportCreateRequestDto.class), examples = {
        @ExampleObject(name = "피드 신고 예시", value = """
                    {
                      "targetType": "FEED",
                      "targetFeedId": "01HXYZABC123456DEF",
                      "reasonType": "AD"
                    }
                """),
        @ExampleObject(name = "댓글 신고 예시", value = """
                    {
                      "targetType": "COMMENT",
                      "targetCommentId": "213",
                      "reasonType": "OBSCENE"
                    }
                """),
        @ExampleObject(name = "사용자 신고 예시", value = """
                    {
                      "targetType": "USER",
                      "targetUserId": "01HXYZABC123456DEF",
                      "reasonType": "DEFAMATION"
                    }
                """) })))
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "신고 접수 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 200,
                      "message": "신고가 정상적으로 접수되었습니다.",
                      "result": true,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "신고 사유는 필수입니다.",
                      "result": false,
                      "data": null
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
public @interface CreateReport {
}