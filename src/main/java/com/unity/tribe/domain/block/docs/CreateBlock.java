package com.unity.tribe.domain.block.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.block.dto.request.BlockCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "사용자 차단", description = "특정 사용자를 차단합니다.")
@RequestBody(description = "차단 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BlockCreateRequestDto.class), examples = @ExampleObject(value = """
        {
          "blockedUserId": "01HRD4CK9PXVT2Q0E4G1BTHVF3"
        }
        """)))
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "사용자 차단 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": true,
                  "message": "사용자가 성공적으로 차단되었습니다.",
                  "data": {
                    "blockId": 1,
                    "userId": "01HRD4CK9PXVT2Q0E4G1BTHVF4",
                    "blockedUser": {
                      "userId": "01HRD4CK9PXVT2Q0E4G1BTHVF3",
                      "nickname": "차단된사용자",
                      "profileImage": "https://example.com/profile.jpg"
                    },
                    "createdAt": "2024-03-20T15:30:00"
                  }
                }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                {
                  "success": false,
                  "message": "이미 차단된 사용자입니다.",
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
public @interface CreateBlock {
}