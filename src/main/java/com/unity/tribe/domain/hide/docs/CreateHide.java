package com.unity.tribe.domain.hide.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.hide.dto.request.HideCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "콘텐츠 숨김", description = "피드 또는 댓글 콘텐츠를 숨김 처리합니다.")
@RequestBody(description = "숨김 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = HideCreateRequestDto.class), examples = {
        @ExampleObject(name = "피드 숨김", value = """
                {
                  "targetType": "FEED",
                  "targetId": "01HRD4CX52RYWX0T6GTP3DV8HN"
                }
                """),
        @ExampleObject(name = "댓글 숨김", value = """
                {
                  "targetType": "COMMENT",
                  "targetId": "456"
                }
                """)
}))
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "콘텐츠 숨김 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": true,
                  "message": "콘텐츠가 성공적으로 숨김 처리되었습니다.",
                  "data": {
                    "hideId": 1,
                    "userId": "01HRD4CX52RYWX0T6GTP4KL9PQ",
                    "targetType": "FEED",
                    "targetId": "01HRD4CX52RYWX0T6GTP3DV8HN",
                    "createdAt": "2025-05-26T15:30:00"
                  }
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": false,
                  "message": "이미 숨김 처리된 콘텐츠입니다.",
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": false,
                  "message": "인증되지 않은 요청입니다.",
                  "data": null
                }
                """)))
})
public @interface CreateHide {
}