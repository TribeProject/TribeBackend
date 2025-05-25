package com.unity.tribe.domain.feed.docs;

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
@Operation(summary = "인증 상세 조회", description = "인증(피드)의 상세 정보를 조회합니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "피드 상세 응답", value = """
                    {
                      "status": 200,
                      "message": "요청이 성공했습니다.",
                      "result": true,
                      "data": {
                        "feedId": "01HXP6T1V9WJDKXEWQY6Y83XTB",
                        "groupId": "01HGW1MHT3RJYK5MXRZ6P5QACN",
                        "userId": "01HGW1MHT3RJYK5MXRZ6P5QACM",
                        "feedType": "CERTIFICATION",
                        "image": "https://unsplash.com/photos/person-running-on-road-during-sunset-vGu08RYjO-s",
                        "content": "인증 피드 본문 내용입니다.",
                        "status": "ACTIVE",
                        "createdAt": "2025-05-25T10:00:00",
                        "updatedAt": "2025-05-25T10:00:00"
                      }
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "인증글을 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "인증글 없음", value = """
                    {
                      "status": 404,
                      "message": "해당 인증글을 찾을 수 없습니다.",
                      "result": false,
                      "data": null
                    }
                """)))
})
public @interface GetFeed {
}