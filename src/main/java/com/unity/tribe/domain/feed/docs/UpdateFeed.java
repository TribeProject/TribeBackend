package com.unity.tribe.domain.feed.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.feed.dto.request.FeedUpdateRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "피드 수정", description = "기존 인증(피드)을 수정합니다.")
@RequestBody(description = "피드 수정 요청 데이터", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeedUpdateRequestDto.class), examples = @ExampleObject(name = "피드 수정 요청 예시", value = """
            {
              \"image\": \"https://unsplash.com/photos/person-running-on-road-during-sunset-vGu08RYjO-s\",
              \"contentText\": \"수정된 인증 피드 본문 내용입니다.\"
            }
        """)))
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "피드 수정 성공 응답", value = """
                    {
                      \"status\": 200,
                      \"message\": \"피드가 성공적으로 수정되었습니다.\",
                      \"result\": true,
                      \"data\": {
                        \"feedId\": \"01HXP6T1V9WJDKXEWQY6Y83XTB\",
                        \"image\": \"https://unsplash.com/photos/person-running-on-road-during-sunset-vGu08RYjO-s\",
                        \"contentText\": \"수정된 인증 피드 본문 내용입니다.\"
                      }
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDto.class), examples = @ExampleObject(name = "피드 없음", value = """
                    {
                      \"status\": 404,
                      \"message\": \"해당 피드를 찾을 수 없습니다.\",
                      \"result\": false,
                      \"data\": null
                    }
                """)))
})
public @interface UpdateFeed {
}