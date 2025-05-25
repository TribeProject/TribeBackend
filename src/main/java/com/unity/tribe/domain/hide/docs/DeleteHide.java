package com.unity.tribe.domain.hide.docs;

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
@Operation(summary = "콘텐츠 숨김 해제", description = "숨김 처리된 콘텐츠의 숨김을 해제합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "콘텐츠 숨김 해제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": true,
                  "message": "숨김이 성공적으로 해제되었습니다.",
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": false,
                  "message": "숨김 처리되지 않은 콘텐츠입니다.",
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
public @interface DeleteHide {
}