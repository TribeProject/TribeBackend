package com.unity.tribe.domain.block.docs;

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
@Operation(summary = "사용자 차단 해제", description = "차단된 사용자의 차단을 해제합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 차단 해제 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": true,
                  "message": "차단이 성공적으로 해제되었습니다.",
                  "data": null
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": false,
                  "message": "차단되지 않은 사용자입니다.",
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
public @interface DeleteBlock {
}