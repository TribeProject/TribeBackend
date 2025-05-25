package com.unity.tribe.domain.feed.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.feed.dto.request.FeedCreateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "피드 생성", description = "새로운 인증을 등록합니다.")
@RequestBody(description = "피드 생성 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeedCreateRequestDto.class), examples = @ExampleObject(value = """
            {
              "feedType": "CERTIFICATION",
              "image": "https://unsplash.com/photos/person-running-on-road-during-sunset-vGu08RYjO-s",
              "content": "인증 피드 본문 내용입니다."
            }
        """)))
@ApiResponses({
        @ApiResponse(responseCode = "201", description = "피드 생성 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 201,
                      "message": "피드가 성공적으로 생성되었습니다.",
                      "result": true,
                      "data": {
                        "feedId": "01HXP6T1V9WJDKXEWQY6Y83XTB",
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "userId": "01HGW1MHT3RJYK5MXRZ6P5QACK",
                        "feedType": "CERTIFICATION",
                        "image": "https://unsplash.com/photos/person-running-on-road-during-sunset-vGu08RYjO-s",
                        "content": "인증 피드 본문 내용입니다.",
                        "status": "ACTIVE",
                        "createdAt": "2025-05-25T10:00:00",
                        "updatedAt": "2025-05-25T10:00:00"
                      }
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "필수 입력값이 누락되었습니다.",
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
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 403,
                      "message": "권한이 없습니다.",
                      "result": false,
                      "data": null
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "모임을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "해당 모임을 찾을 수 없습니다.",
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
public @interface CreateFeed {
}