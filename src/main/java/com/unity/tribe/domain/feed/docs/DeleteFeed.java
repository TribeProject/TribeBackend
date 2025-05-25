package com.unity.tribe.domain.feed.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "피드 삭제", description = "기존 인증(피드)을 삭제합니다.")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "삭제 성공", value = """
                    {
                      \"status\": 200,
                      \"message\": \"피드가 성공적으로 삭제되었습니다.\",
                      \"result\": true,
                      \"data\": null
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "피드를 찾을 수 없음", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "피드 없음", value = """
                    {
                      \"status\": 404,
                      \"message\": \"해당 피드를 찾을 수 없습니다.\",
                      \"result\": false,
                      \"data\": null
                    }
                """)))
})
public @interface DeleteFeed {
}