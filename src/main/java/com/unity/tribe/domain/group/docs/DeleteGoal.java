package com.unity.tribe.domain.group.docs;

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
@Operation(summary = "모임 목표 삭제", description = "모임의 목표를 삭제합니다. 모임의 호스트만 삭제할 수 있습니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 200,
                  "message": "목표가 성공적으로 삭제되었습니다.",
                  "result": true,
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
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 403,
                  "message": "목표를 삭제할 권한이 없습니다.",
                  "result": false,
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "404", description = "목표를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "status": 404,
                  "message": "해당 목표를 찾을 수 없습니다.",
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
public @interface DeleteGoal {
}