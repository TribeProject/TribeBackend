package com.unity.tribe.domain.auth.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.auth.dto.request.DevTokenRequestDto;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestBody(description = "개발용 토큰 발급 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DevTokenRequestDto.class), examples = @ExampleObject(value = """
            {
              "nickname": "테스트유저"
            }
        """)))
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "토큰 발급 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 200,
                      "message": "개발용 토큰이 성공적으로 발급되었습니다.",
                      "result": true,
                      "data": {
                        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMUhOUEtHMTUzWUNKTjNBNVhFQkJSV0UxUSIsImlhdCI6MTY5OTkyOTYwMCwiZXhwIjoxNzAwMDE2MDAwfQ.XYZ123...",
                        "tokenType": "Bearer",
                        "expiresIn": 86400,
                        "userId": "01HNPKG153YCJN3A5XEBBRWE1Q",
                        "nickname": "테스트유저"
                      }
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "닉네임은 필수입니다.",
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
public @interface DevTokenApi {
}